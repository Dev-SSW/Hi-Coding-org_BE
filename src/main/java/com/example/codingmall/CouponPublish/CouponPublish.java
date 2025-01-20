package com.example.codingmall.CouponPublish;

import com.example.codingmall.Coupon.Coupon;
import com.example.codingmall.Coupon.CouponRequest;
import com.example.codingmall.Coupon.CouponStatus;
import com.example.codingmall.User.User;
import com.nimbusds.oauth2.sdk.util.singleuse.AlreadyUsedException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CouponPublish {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponpublish_id")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private CouponPublishStatus publishStatus;    //발급 상태
    private LocalDateTime useDate;                //사용 일시
    private LocalDateTime registerDate;           //발급 일시
    private LocalDateTime updateDate;             //수정 일시

    /* 생성 메서드 (정적 팩토리) */
    public static CouponPublish couponPublishToUser(User user, Coupon coupon) {
        coupon.minusRemain(); // 쿠폰 발급 시 잔여량 감소
        return CouponPublish.builder()
                .user(user)
                .coupon(coupon)
                .publishStatus(CouponPublishStatus.available)  //발급 상태 (사용 가능, 불가능)
                .registerDate(LocalDateTime.now())             //발급 일시
                .updateDate(LocalDateTime.now())               //수정 일시
                .build();
    }

    /* 쿠폰 사용 */
    public void useCoupon() throws AlreadyUsedException {
        if (this.publishStatus == CouponPublishStatus.available) {
            this.publishStatus  = CouponPublishStatus.unavailable; // 쿠폰 사용 시 사용 불가능으로 수정
            this.useDate = LocalDateTime.now();                //사용 일시
        }
        else {
            throw new AlreadyUsedException("이미 사용한 쿠폰입니다.");
        }
    }
}

