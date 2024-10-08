package com.example.codingmall.Payment;

import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Table(name = "Payment")
@Entity@Getter
public class Payment {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @JoinColumn(name = "order_id")
    @OneToOne
    private Order order;
    private Status status;
    private enum Status{
        //배송 전, 배송 중, 배송 완료
        Pending, OnTheWay, Complete
    }
    private String deliveryRequest;
    private LocalDateTime deliveryDate;
    private LocalDateTime expectedDeliveryDate; // 예상 배송일
    private int deliveryFee; // 배송비
}
