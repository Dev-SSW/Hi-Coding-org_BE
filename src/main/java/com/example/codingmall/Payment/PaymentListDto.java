package com.example.codingmall.Payment;

import com.example.codingmall.Delivery.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentListDto {
    private Long orderId;
    private int amount;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private DeliveryStatus deliveryStatus;

    @Builder
    public PaymentListDto(Payment payment){
        this.orderId = payment.getOrder().getId();
        this.amount = payment.getAmount();
        this.status = payment.getStatus();
        this.paymentDate = payment.getPaymentDate();
        this.deliveryStatus = payment.getOrder() != null && payment.getOrder().getDelivery() != null
                ? payment.getOrder().getDelivery().getStatus(): null; // Order나 Delivery가 null인 경우 null로 설정
    }
}