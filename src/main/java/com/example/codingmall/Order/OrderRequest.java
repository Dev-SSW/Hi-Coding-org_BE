package com.example.codingmall.Order;

import com.example.codingmall.OrderItem.OrderItemRequest;
import lombok.*;

import java.util.List;

@Getter
public class OrderRequest {
    private List<OrderItemRequest> orderItems;

    private String receiverName;    //수령자 이름
    private String receiverPhone;   //수령자 폰
    private String deliveryAddress; //배송지
    private String orderNote;       //메모
}
