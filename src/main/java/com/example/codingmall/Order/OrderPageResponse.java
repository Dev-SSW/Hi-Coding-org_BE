package com.example.codingmall.Order;

import com.example.codingmall.Coupon.CouponResponse;
import com.example.codingmall.CouponPublish.CouponPublish;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.OrderItem.OrderItemResponse;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserInfoResponse;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderPageResponse {
    private UserInfoResponse userInfo; // 유저 정보
    private List<OrderItemResponse> orderItems; // 주문 상품 목록
    private List<CouponResponse> coupons; // 사용 가능한 쿠폰 목록

    public static OrderPageResponse of(UserInfoResponse userInfo, List<CouponResponse> coupons, List<OrderItemResponse> orderItems) {
        return OrderPageResponse.builder()
                .userInfo(userInfo)
                .coupons(coupons)
                .orderItems(orderItems)
                .build();
    }
}
