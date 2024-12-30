package com.example.codingmall.Coupon;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CouponRequest {
    private String couponName;          // 쿠폰명
    private BigDecimal discountAmount;  // 할인 금액
    private BigDecimal discountPercent; // 할인율
    private String intro ;              // 설명
    private BigDecimal couponAmount;    // 쿠폰 총량
    private LocalDateTime startTime;    // 적용 시작일
    private LocalDateTime endTime;      // 만료일
}
