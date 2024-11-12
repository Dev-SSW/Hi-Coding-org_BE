package com.example.codingmall.Order;

import com.example.codingmall.Coupon.Coupon;
import com.example.codingmall.Delivery.Delivery;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    private Delivery delivery;

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

    /* 연관 관계 편의 메서드 */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);  // `order` 설정
    }
    public void setUser(User user) { this.user = user; }

    /* 비즈니스 로직 */
    public void cancel() {
        this.orderStatus = OrderStatus.CANCEL;
        this.isCancelled = true;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /* 조회 로직 */
    public int getTotalPrice() {
        this.totalAmount = 0;
        for (OrderItem orderItem : orderItems) {
            this.totalAmount += orderItem.getTotalPrice();
        }
        return this.totalAmount;
    }
}

