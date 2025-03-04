package com.example.codingmall.Order;

import com.example.codingmall.Cart.Cart;
import com.example.codingmall.Cart.CartRepository;
import com.example.codingmall.Coupon.CouponResponse;
import com.example.codingmall.CouponPublish.CouponPublish;
import com.example.codingmall.CouponPublish.CouponPublishRepository;
import com.example.codingmall.CouponPublish.CouponPublishService;
import com.example.codingmall.CouponPublish.CouponPublishStatus;
import com.example.codingmall.Exception.CouponNotFoundException;
import com.example.codingmall.Item.Item;
import com.example.codingmall.Item.ItemRepository;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.OrderItem.OrderItemRequest;
import com.example.codingmall.OrderItem.OrderItemResponse;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserInfoResponse;
import com.nimbusds.oauth2.sdk.util.singleuse.AlreadyUsedException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CouponPublishRepository couponPublishRepository;
    private final CouponPublishService couponPublishService;

    /* 개별 바로 주문 */
    @Transactional
    public Long createOrder(User user, OrderRequest orderRequest, Long couponPublishId) {
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> {
                    Item item = itemRepository.findItemById(orderItemRequest.getItemId());
                    item.removeStock(orderItemRequest.getItemCount());
                    return OrderItem.createOrderItem(item, orderItemRequest.getItemCount());
                })
                .collect(Collectors.toList());

        // 총 금액 계산
        int totalAmount = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

        // 쿠폰 적용
        CouponPublish couponPublish = null;
        if (couponPublishId != null) {
            couponPublish = couponPublishRepository.findById(couponPublishId)
                    .orElseThrow(() -> new CouponNotFoundException("유효하지 않은 쿠폰입니다."));

            if (couponPublish.getPublishStatus() == CouponPublishStatus.available) {
                totalAmount -= couponPublish.getCoupon().getDiscountAmount().intValue();
                try {
                    couponPublish.useCoupon();
                } catch (AlreadyUsedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Order 객체를 생성 (totalAmount는 createOrder에서 자동으로 계산됨)
        Order order = Order.createOrder(user, orderRequest, orderItems, totalAmount);

        // Order의 OrderItem을 연결
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /* 장바구니 상품들 주문 */
    @Transactional
    public Long createOrderFromCart(User user, OrderCartRequest orderCartRequest, Long couponPublishId) {
        Cart cart = cartRepository.findCartByUser(user);

        List<OrderItem> orderItems = cart.getItems().stream() //Cart 안의 CartItem을 순환
                .map(cartItem -> {
                    Item item = cartItem.getItem();
                    item.removeStock(cartItem.getCount());
                    return OrderItem.createOrderItem(item, cartItem.getCount());
                })
                .collect(Collectors.toList());

        // 총 금액 계산
        int totalAmount = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)  // 각 OrderItem의 총 금액을 합산
                .sum();

        // 쿠폰 적용
        CouponPublish couponPublish = null;
        if (couponPublishId != null) {
            couponPublish = couponPublishRepository.findById(couponPublishId)
                    .orElseThrow(() -> new CouponNotFoundException("유효하지 않은 쿠폰입니다."));

            if (couponPublish.getPublishStatus() == CouponPublishStatus.available) {
                totalAmount -= couponPublish.getCoupon().getDiscountAmount().intValue();
                try {
                    couponPublish.useCoupon();
                } catch (AlreadyUsedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        OrderRequest orderRequest1 = OrderRequest.builder()
                .receiverName(orderCartRequest.getReceiverName())
                .receiverPhone(orderCartRequest.getReceiverPhone())
                .deliveryAddress(orderCartRequest.getDeliveryAddress())
                .orderNote(orderCartRequest.getOrderNote())
                .build();

        Order order = Order.createOrder(user, orderRequest1, orderItems, totalAmount);

        // Order의 OrderItem을 연결
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }

        orderRepository.save(order);
        cartRepository.delete(cart); // 주문 후 장바구니 비우기
        return order.getId();
    }

    /* 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        order.cancel();
    }

    /* 주문 상세 페이지 조회 */
    public OrderPageResponse getOrderPageData(User user, List<OrderItemRequest> orderItems) {
        // 1. User 정보 가져오기
        UserInfoResponse userInfoResponse = UserInfoResponse.from(user);

        // 2. User가 보유한 쿠폰 정보 가져오기
        List<CouponResponse> couponResponses = couponPublishService.getCouponPublish(user).stream()
                .map(CouponResponse::from)
                .collect(Collectors.toList());

        // 3. OrderItemRequest -> OrderItemResponse 변환
        List<OrderItemResponse> orderItemResponses = orderItems.stream()
                .map(orderItemRequest -> {
                    Item item = itemRepository.findItemById(orderItemRequest.getItemId()); // 아이템 조회
                    return OrderItemResponse.builder()
                            .itemId(item.getId())
                            .itemName(item.getProductName())
                            .itemCount(orderItemRequest.getItemCount())
                            .totalPrice(item.getPrice() * orderItemRequest.getItemCount())
                            .build();
                })
                .collect(Collectors.toList());

        // 4. DTO로 변환 후 반환
        return OrderPageResponse.of(userInfoResponse, couponResponses, orderItemResponses);
    }

    /* 주문 내역 조회 */
    public List<OrderHistoryResponse> getOrderHistoryData(User user) {
        // 1. 사용자의 주문 목록 조회
        List<Order> orders = orderRepository.findByUser(user);

        // 2. 주문 목록을 OrderHistoryResponse로 변환
        return orders.stream()
                .map(order -> OrderHistoryResponse.builder()
                        .orderId(order.getId())
                        .orderDate(order.getOrderDate())
                        .orderItemList(order.getOrderItems().stream()
                                .map(orderItem -> OrderItemResponse.builder()
                                        .itemId(orderItem.getItem().getId())
                                        .itemName(orderItem.getItem().getProductName())
                                        .itemCount(orderItem.getItemCount())
                                        .totalPrice(orderItem.getTotalPrice())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
