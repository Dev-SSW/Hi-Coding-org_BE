package com.example.codingmall.Coupon;

import com.example.codingmall.Order.Order;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "Coupon")
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    private String couponName; // 쿠폰명
    private CouponStatus couponStatus; // 상태코드

    private BigDecimal discountAmount; //할인금액
    private BigDecimal discountPercent; // 할인율

    private String intro ; // 설명
    private BigDecimal remain; // 잔여 쿠폰량
    private BigDecimal couponAmount; // 쿠폰 총량

    private LocalDateTime startTime; // 적용 시작일
    private LocalDateTime endTime; // 만료일

    private LocalDateTime registerTime; // 등록일시
    private LocalDateTime updateTime; //수정일시

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 쿠폰 등록자 아이디

    private enum CouponStatus{
        ACTIVE,
        EXPIRED,
        UNUSED,
        USED

    }
}
