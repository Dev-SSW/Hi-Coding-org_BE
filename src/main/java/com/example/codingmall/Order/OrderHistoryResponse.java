package com.example.codingmall.Order;

import com.example.codingmall.OrderItem.OrderItemResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistoryResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> orderItemList;
}
