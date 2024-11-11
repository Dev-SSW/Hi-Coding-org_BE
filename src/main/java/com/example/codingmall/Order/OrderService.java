package com.example.codingmall.Order;

import com.example.codingmall.Item.Item;
import com.example.codingmall.Item.ItemRepository;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.User.User;
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

    /* 개별 바로 주문 */
    @Transactional
    public Long createOrder(User user, OrderRequest orderRequest) {
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> {
                    Item item = itemRepository.findById(orderItemRequest.getItemId())
                            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
                    item.removeStock(orderItemRequest.getItemCount());
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
}
