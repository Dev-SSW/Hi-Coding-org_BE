package com.example.codingmall.Coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponScheduler {
    private final CouponService couponService;

    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void scheduleBirthdayCoupons() {
        couponService.publishBirthCouponAuto();
    }
}
