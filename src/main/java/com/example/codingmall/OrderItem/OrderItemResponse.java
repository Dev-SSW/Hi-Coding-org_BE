package com.example.codingmall.OrderItem;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemResponse {
    private Long itemId;    //아이템 ID
    private String itemName;//아이템 이름
    private int itemCount;  //아이템 개수
    private int totalPrice; //상품의 총 가격

    public static OrderItemResponse from(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .itemId(orderItem.getItem().getId())
                .itemName(orderItem.getItem().getProductName())
                .itemCount(orderItem.getItemCount())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
