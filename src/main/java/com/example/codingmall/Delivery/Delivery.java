package com.example.codingmall.Delivery;

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
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @JoinColumn(name = "order_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    private int amount;

    private boolean status;
    private LocalDateTime paymentDate; //결제일
    private String cardCompany;

    private PaymentMethod paymentMethod; // 결제 방법

    public void updateStatus(boolean status){
        this.status = status;
    }
    public void setPaymentDate(LocalDateTime localDateTime){
        this.paymentDate = localDateTime;
    }

    @Builder
    public Delivery(Order order, int amount, boolean status,
                    LocalDateTime paymentDate, String cardCompany,
                    PaymentMethod paymentMethod) {
        this.order = order;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.cardCompany = cardCompany;
        this.paymentMethod = paymentMethod;
    }
}
