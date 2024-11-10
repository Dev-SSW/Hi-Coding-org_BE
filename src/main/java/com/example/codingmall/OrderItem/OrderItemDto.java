package com.example.codingmall.OrderItem;

import com.example.codingmall.Item.Item;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OrderItemDto {
    private Long itemId;
    private int itemCount;

    public OrderItem toEntity() {
        return OrderItem.builder()
                .itemCount(itemCount)
                .build();
    }
}
