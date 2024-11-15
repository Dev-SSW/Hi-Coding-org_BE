package com.example.codingmall.Payment;

import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private Order order;

    private int amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // 결제 상태
    private LocalDateTime paymentDate; //결제일
    private String cardCompany;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // 결제 방법

    public void updateStatus(PaymentStatus status){
        this.status = status;
    }
    public void setPaymentDate(LocalDateTime localDateTime){
        this.paymentDate = localDateTime;
    }

    @Builder
    public Payment(Order order, int amount, PaymentStatus status,
                   LocalDateTime paymentDate, String cardCompany,
                   PaymentMethod paymentMethod) {
        this.order = order;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.cardCompany = cardCompany;
        this.paymentMethod = paymentMethod;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
