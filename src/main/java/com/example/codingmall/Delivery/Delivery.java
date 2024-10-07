package com.example.codingmall.Delivery;

import com.example.codingmall.Orders.Order;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.time.LocalDateTime;

@Entity
@Getter@Table(name = "Delivery")
public class Delivery {
    @Id@GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @JoinColumn(name = "order_id")
    @OneToOne
    private Order order;

    private int amount;

    private boolean status;
    private LocalDateTime paymentDate; //결제일
    private String cardCompany;

    private paymentMethod paymentMethod; // 결제 방법

    private enum paymentMethod {
        // 나중에는 아임포트 api 사용하기
        // 무통장 입금, 카드 , 간편결제
        bankbook,card,simple
    }

}