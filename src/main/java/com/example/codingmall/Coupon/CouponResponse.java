package com.example.codingmall.Coupon;

import com.example.codingmall.CouponPublish.CouponPublish;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponResponse {
    private Long couponId;        //쿠폰 ID
    private String couponName;    //쿠폰 이름
    private int discountAmount;   //할인 가격
    private String status;        //쿠폰 상태

    public static CouponResponse from(CouponPublish couponPublish) {
        return CouponResponse.builder()
                .couponId(couponPublish.getCoupon().getId())
                .couponName(couponPublish.getCoupon().getCouponName())
                .discountAmount(couponPublish.getCoupon().getDiscountAmount().intValue())
                .status(couponPublish.getPublishStatus().name())
                .build();
    }

}
