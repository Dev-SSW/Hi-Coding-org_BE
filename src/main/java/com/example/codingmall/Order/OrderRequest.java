package com.example.codingmall.Order;

import com.example.codingmall.OrderItem.OrderItemRequest;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequest {
    private List<OrderItemRequest> orderItems;

    private String receiverName;    //수령자 이름
    private String receiverPhone;   //수령자 폰
    private String deliveryAddress; //배송지
    private String orderNote;       //메모

    // 필수 정보만 받는 생성자: orderItems 제외
    public OrderRequest orderRequest(String receiverName, String receiverPhone, String deliveryAddress, String orderNote) {
        return OrderRequest.builder()
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .deliveryAddress(deliveryAddress)
                .orderNote(orderNote)
                .build();
    }
}
