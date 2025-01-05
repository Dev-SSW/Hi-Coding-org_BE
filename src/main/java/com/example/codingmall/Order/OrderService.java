package com.example.codingmall.Order;

import com.example.codingmall.Cart.Cart;
import com.example.codingmall.Cart.CartRepository;
import com.example.codingmall.Item.Item;
import com.example.codingmall.Item.ItemRepository;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.User.User;
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

    /* 개별 바로 주문 */
    @Transactional
    public Long createOrder(User user, OrderRequest orderRequest) {
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> {
                    Item item = itemRepository.findItemById(orderItemRequest.getItemId());
                    //item.removeStock(orderItemRequest.getItemCount()); --> 일단 테스트를 위해 잠시만 꺼놓음.
                    return OrderItem.createOrderItem(item, orderItemRequest.getItemCount());
                })
                .collect(Collectors.toList());

        // Order 객체를 생성 (totalAmount는 createOrder에서 자동으로 계산됨)
        Order order = Order.createOrder(user, orderRequest, orderItems);

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
    public Long createOrderFromCart(User user, OrderCartRequest orderCartRequest) {
        Cart cart = cartRepository.findCartByUser(user);

        List<OrderItem> orderItems = cart.getItems().stream() //Cart 안의 CartItem을 순환
                .map(cartItem -> {
                    Item item = cartItem.getItem();
                    item.removeStock(cartItem.getCount());
                    return OrderItem.createOrderItem(item, cartItem.getCount());
                })
                .collect(Collectors.toList());

        OrderRequest orderRequest1 = OrderRequest.builder()
                .receiverName(orderCartRequest.getReceiverName())
                .receiverPhone(orderCartRequest.getReceiverPhone())
                .deliveryAddress(orderCartRequest.getDeliveryAddress())
                .orderNote(orderCartRequest.getOrderNote())
                .build();

        Order order = Order.createOrder(user, orderRequest1, orderItems);

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
}
