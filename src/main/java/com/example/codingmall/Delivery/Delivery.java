package com.example.codingmall.Delivery;

import com.example.codingmall.Order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
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
