package com.example.codingmall.Order;

import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.OrderItem.OrderItemDto;
import com.example.codingmall.User.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OrderDto {
    private List<OrderItemDto> orderItems;

    private String receiverName;    //수령자 이름
    private String receiverPhone;   //수령자 폰
    private String deliveryAddress; //배송지
    private String orderNote;       //메모

    public Order toEntity() {
        return Order.builder()
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .deliveryAddress(deliveryAddress)
                .orderNote(orderNote)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .isCancelled(false)
                .isPaid(false)
                .build();
    }
}
