package com.example.codingmall.OrderItem;

import com.example.codingmall.Item.Item;
import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문번호

    private int itemCount; // 주문상품 갯수
    private int totalPrice; // 총 가격

    private LocalDateTime registerDate; // 등록일시
    private LocalDateTime updateDate; // 수정일시

    private Status status; // 주문상품 상태

    private enum Status{
        // 취소, 결제 대기중(무통장), 고객 주문 , 결제 완료, 발송 확인, 배송 중, 배송 완료, 구매확정
        cancelled,payment_standby,ordered,payment_complete,delivering,delivered, fixed
    }

}
