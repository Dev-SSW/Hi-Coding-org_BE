package com.example.codingmall.Order;

import com.example.codingmall.Coupon.Coupon;
import com.example.codingmall.Payment.Payment;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Orders") // order은 mysql 예약어라서 테이블 이름을 orders로 변경
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name ="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)           //엔티티에 관련된 모든 엔티티를 함께 영속화 시킨다 (Delivery)
    @JoinColumn(name = "delivery_id")               //Delivery의 PK의 주인은 Order이다
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder.Default // 오류 해결 위한 추가 부분(List 필드에 초깃값을 설정했지만, @Builder에서 무시함 --> 이 내용을 해결)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String receiverName;    //수령자 이름
    private String receiverPhone;   //수령자 폰
    private String deliveryAddress; //배송지
    private String orderNote;       //메모
    private int totalAmount;        //총가격

    private boolean isCancelled;    //취소 여부
    private boolean isPaid;         //결제 여부

    /* 생성 메서드 (정적 팩토리)*/
    public static Order createOrder(User user, OrderRequest orderRequest, List<OrderItem> orderItems) {
        // Order 객체를 생성하면서 필드 초기화
        int totalAmount = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)  // 각 OrderItem의 총 금액을 합산
                .sum();
        return Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .receiverName(orderRequest.getReceiverName())
                .receiverPhone(orderRequest.getReceiverPhone())
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .orderNote(orderRequest.getOrderNote())
                .totalAmount(totalAmount)
                .orderItems(orderItems)
                .isCancelled(false)
                .isPaid(false)
                .build();
    }

    /* 비즈니스 로직 */
    public void cancel() {
        this.orderStatus = OrderStatus.CANCEL;
        this.isCancelled = true;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}

