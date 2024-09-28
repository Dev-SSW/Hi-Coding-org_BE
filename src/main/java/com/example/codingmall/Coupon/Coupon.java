package com.example.codingmall.Coupon;

import com.example.codingmall.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "Coupon")
public class Coupon {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String couponName;
    private BigDecimal discountAmount; //할인금액

    private BigDecimal discountPercent; // 할인율
    private String intro ; // 설명

    private CouponStatus couponStatus; // 상태코드

    private BigDecimal remain;
    private LocalDateTime startTime; // 적용 시작일
    private LocalDateTime endTime; // 만료일

    private LocalDateTime registerTime; // 등록일시
    private LocalDateTime updateTime; //수정일시


    @OneToMany(mappedBy = "coupon")
    private List<Order> orders;


    private enum CouponStatus{
        ACTIVE,
        EXPIRED,
        USED

    }
}
