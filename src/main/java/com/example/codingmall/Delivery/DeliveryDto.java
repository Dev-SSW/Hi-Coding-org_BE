package com.example.codingmall.Delivery;

import com.example.codingmall.Order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryDto {
    private Order order;
    private int amount;
    private boolean status;
    private LocalDateTime paymentDate;
    private String cardCompany;
    private PaymentMethod paymentMethod;
    @Builder
    public DeliveryDto(Order order, int amount, boolean status,
                                 LocalDateTime paymentDate, String cardCompany,
                                 PaymentMethod paymentMethod) {
        this.order = order;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.cardCompany = cardCompany;
        this.paymentMethod = paymentMethod;
    }
     // DTO --> 엔티티로 변환 메서드
     public Delivery toEntity() {
        return Delivery.builder()
                .order(order)
                .amount(amount)
                .status(status)
                .paymentDate(paymentDate)
                .cardCompany(cardCompany)
                .paymentMethod(paymentMethod)
                .build();
        }
}
