package com.example.codingmall.Delivery;

import lombok.Getter;

import java.time.LocalDateTime;

// 배송 정보 조회 응답을 위한 DTO
@Getter
public class DeliveryResponseDto {
    private Long id;
    private Long orderId;
    private DeliveryStatus deliveryStatus;
    private String deliveryRequest;
    private LocalDateTime deliveryStartDate;
    private LocalDateTime expectedDeliveryDate;
    private int deliveryFee;

    public DeliveryResponseDto (Delivery delivery){
        this.id = delivery.getId();
        this.orderId = delivery.getOrder().getId();
        this.deliveryStatus = delivery.getStatus();
        this.deliveryRequest = delivery.getDeliveryRequest();
        this.deliveryStartDate = delivery.getDeliveryStartDate();
        this.expectedDeliveryDate = delivery.getExpectedDeliveryDate();
        this.deliveryFee = delivery.getDeliveryFee();
    }
}
