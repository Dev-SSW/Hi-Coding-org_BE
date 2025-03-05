package com.example.codingmall.Coupon;

import com.example.codingmall.CouponPublish.CouponPublish;
import com.example.codingmall.CouponPublish.CouponPublishService;
import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    private final CouponPublishService couponPublishService;

    /* 쿠폰 만들기 */
    @PostMapping("/admin/coupon/create")
    @Operation(summary = "새로운 쿠폰 만들기")
    public ResponseEntity<String> couponCreate(@RequestBody CouponRequest couponRequest) {
        couponService.couponCreate(couponRequest);
        return ResponseEntity.ok("쿠폰이 생성되었습니다.");
    }

    /* 특정 유저에게 쿠폰 발급 */
    @PostMapping("/admin/coupon/publish")
    @Operation(summary = "특정 유저에게 쿠폰 발급")
    public ResponseEntity<String> couponPublish(@RequestParam(name = "couponId") Long couponId, @RequestParam(name = "userId") Long userId) {
        couponService.couponPublish(couponId, userId);
        return ResponseEntity.ok("유저에게 쿠폰이 발급되었습니다.");
    }

    /* 생일 쿠폰 수동 발급 (테스트) */
    @GetMapping("/admin/coupon/birthday")
    @Operation(summary = "생일 쿠폰 발급 수동으로 요청")
    public ResponseEntity<String> publishBirthCoupon() {
        couponService.publishBirthCouponAuto();
        return ResponseEntity.ok("생일 쿠폰이 발급되었습니다.");
    }

    /* 유저가 소유한 모든 쿠폰 확인 */
    @GetMapping("/user/coupon/search")
    @Operation(summary = "유저가 소유한 모든 쿠폰 확인")
    public ResponseEntity<List<CouponResponse>> getAllCouponFromUser(@AuthenticationPrincipal User user) {
        List<CouponResponse> couponResponses = couponPublishService.getCouponPublish(user).stream()
                .map(CouponResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(couponResponses);
    }

    /* 만들어진 모든 쿠폰 확인 */
    @GetMapping("/admin/coupon/searchAll")
    @Operation(summary = "만들어진 모든 쿠폰 확인")
    public ResponseEntity<List<Coupon>> getAllCoupon() {
        return ResponseEntity.ok(couponService.getAllCoupon());
    }
}
