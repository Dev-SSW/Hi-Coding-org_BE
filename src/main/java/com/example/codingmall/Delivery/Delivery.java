package com.example.codingmall.Delivery;

import com.example.codingmall.Exception.DeliveryNotCancelException;
import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @JoinColumn(name = "order_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private String deliveryRequest; // 배송 요청사항
    private LocalDateTime deliveryStartDate; // 배송 시작일
    private LocalDateTime expectedDeliveryDate; // 예상 배송일
    private int deliveryFee = 3000; // 배송비

    public void startDelivery(){
        this.status = DeliveryStatus.OnTheWay;
        this.deliveryStartDate = LocalDateTime.now();
    }
    public void completeDelivery(){
        this.status = DeliveryStatus.Complete;
    }

    public void cancelDelivery(){
        if (this.status == DeliveryStatus.OnTheWay){
            throw new DeliveryNotCancelException("배송 중인 주문은 취소할 수 없습니다. 현재 상태 : " + this.status);
        }
        this.status = DeliveryStatus.Cancelled;
    }
    public static Delivery createDelivery(Order order,DeliveryDto deliveryDto){
        return Delivery.builder()
                .order(order)
                .deliveryRequest(deliveryDto.getDeliveryRequest())
                .expectedDeliveryDate(deliveryDto.getExpectedDeliveryDate())
                .deliveryFee(deliveryDto.getDeliveryFee())
                .build();
    }
    @Builder
    public Delivery(Order order, String deliveryRequest,LocalDateTime expectedDeliveryDate, int deliveryFee){
        this.order = order;
        this.deliveryRequest = deliveryRequest;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.deliveryFee = deliveryFee;
        this.status = DeliveryStatus.Pending;
    }
}
