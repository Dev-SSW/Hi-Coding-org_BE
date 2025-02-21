package com.example.codingmall.Coupon;

import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    /* 쿠폰 만들기 */
    @PostMapping("/create")
    @Operation(summary = "새로운 쿠폰 만들기")
    public ResponseEntity<String> couponCreate(@AuthenticationPrincipal User user, @RequestBody CouponRequest couponRequest) {
        couponService.couponCreate(user, couponRequest);
        return ResponseEntity.ok("쿠폰이 생성되었습니다.");
    }

    /* 특정 유저에게 쿠폰 발급 */
    @PostMapping("/publish")
    @Operation(summary = "특정 유저에게 쿠폰 발급")
    public ResponseEntity<String> couponPublish(@AuthenticationPrincipal User user, @RequestParam("couponId") Long couponId) {
        couponService.couponPublish(user, couponId);
        return ResponseEntity.ok("쿠폰이 발급되었습니다.");
    }

    /* 생일 쿠폰 수동 발급 (테스트) */
    @GetMapping("/birthday")
    @Operation(summary = "생일 쿠폰 수동 발급 (테스트)")
    public ResponseEntity<String> publishBirthCouponAuto() {
        couponService.publishBirthCouponAuto();
        return ResponseEntity.ok("생일 쿠폰이 발급되었습니다.");
    }
}
