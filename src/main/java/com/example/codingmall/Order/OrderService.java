package com.example.codingmall.Order;

import com.example.codingmall.Item.Item;
import com.example.codingmall.Item.ItemRepository;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.OrderItem.OrderItemDto;
import com.example.codingmall.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /* 주문 */
    @Transactional
    public CreateOrderResponse createOrder(User user, OrderDto orderDto) {
        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                .map(orderItemDto -> {
                    Item item = itemRepository.findById(orderItemDto.getItemId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Item ID 입니다."));
                    OrderItem orderItem = orderItemDto.toEntity();
                    orderItem.setItem(item);
                    orderItem.setOrderPrice(item.getPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        Order order = orderDto.toEntity();
        order.setUser(user);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);  //주문에 주문 상품들을 주입
        }
        order.getTotalPrice();

        //orderItems.forEach(order::addOrderItem);
        orderRepository.save(order);
        return CreateOrderResponse.builder()
                .username(user.getUsername())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .deliveryAddress(order.getDeliveryAddress())
                .orderNote(order.getOrderNote())
                .totalAmount(order.getTotalAmount())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .isCancelled(order.isCancelled())
                .isPaid(order.isPaid())
                .build();
    }
}
