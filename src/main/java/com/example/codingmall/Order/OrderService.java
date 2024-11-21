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
                    return OrderItem.createOrderItem(item, orderItemRequest.getItemCount());  // null은 orderId가 없으므로 임시로 처리
                })
                .collect(Collectors.toList());
        // Order 객체를 생성 (totalAmount는 createOrder에서 자동으로 계산됨)
        Order order = Order.createOrder(user, orderRequest, orderItems);

        // Order의 OrderItem을 연결
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);  // 각 OrderItem에 해당하는 Order를 연결
        }

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /* 장바구니 상품들 주문 */
    @Transactional
    public Long createOrderFromCart(User user, OrderRequest orderRequest) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new IllegalStateException("유저의 장바구니를 찾지 못 했습니다."));
        List<OrderItem> orderItems = cart.getItems().stream() //Cart 안의 CartItem을 순환
                .map(cartItem -> {
                    Item item = cartItem.getItem();
                   // item.removeStock(cartItem.getCount()); --> 테스트를 위해 잠시 꺼놓음.
                    return OrderItem.createOrderItem(item, cartItem.getCount());
                })
                .collect(Collectors.toList());
        Order order = Order.createOrder(user, orderRequest, orderItems);

        // Order의 OrderItem을 연결
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);  // 각 OrderItem에 해당하는 Order를 연결
        }

        orderRepository.save(order);
        cartRepository.delete(cart); // 주문 후 장바구니 비우기
        return order.getId();
    }

    /* 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문을 찾지 못했습니다."));
        order.cancel();
    }
}
