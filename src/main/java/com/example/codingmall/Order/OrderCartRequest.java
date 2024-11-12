package com.example.codingmall.Order;

import lombok.Getter;

@Getter
public class OrderCartRequest {
    private String receiverName;    //수령자 이름
    private String receiverPhone;   //수령자 폰
    private String deliveryAddress; //배송지
    private String orderNote;       //메모

}
