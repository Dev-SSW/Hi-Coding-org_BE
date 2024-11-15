package com.example.codingmall.Payment;

import com.example.codingmall.Order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentDto {
    private Long orderId;
    private int amount;
    private boolean status;
    private LocalDateTime paymentDate;
    private String cardCompany;
    private PaymentMethod paymentMethod;
    @Builder
    public PaymentDto(Long orderId, int amount, boolean status,
                      LocalDateTime paymentDate, String cardCompany,
                      PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.cardCompany = cardCompany;
        this.paymentMethod = paymentMethod;
    }
}
