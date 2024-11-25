package com.example.codingmall.Delivery;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
 // 배송 생성 요청을 위한 Dto
@Getter
@NoArgsConstructor
public class DeliveryDto {
    private Long orderId;
    private String deliveryRequest;
    private LocalDateTime expectedDeliveryDate;
    private int deliveryFee;

    @Builder
    public DeliveryDto(Long orderId, String deliveryRequest, LocalDateTime expectedDeliveryDate,int deliveryFee){
        this.orderId = orderId;
        this.deliveryRequest = deliveryRequest;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.deliveryFee = deliveryFee;
    }
}
