package com.example.codingmall.Coupon;

import com.example.codingmall.Exception.CouponRemainZeroException;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    private String couponName;          //쿠폰명
    private CouponStatus couponStatus;  //상태코드

    private BigDecimal discountAmount;  //할인금액
    private BigDecimal discountPercent; //할인율

    private String intro ;              //설명
    private BigDecimal remain;          //잔여 쿠폰량
    private BigDecimal couponAmount;    //쿠폰 총량

    private LocalDateTime startTime;    //적용 시작일
    private LocalDateTime endTime;      //만료일

    private LocalDateTime registerTime; //등록 일시
    private LocalDateTime updateTime;   //수정 일시

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 쿠폰 등록자 아이디

    /* 생성 메서드 (정적 팩토리) */
    public static Coupon createCoupon(User user, CouponRequest couponRequest) {
        return Coupon.builder()
                .couponName(couponRequest.getCouponName())
                .discountAmount(couponRequest.getDiscountAmount())
                .discountPercent(couponRequest.getDiscountPercent())
                .intro(couponRequest.getIntro())
                .couponAmount(couponRequest.getCouponAmount())
                .startTime(couponRequest.getStartTime())
                .endTime(couponRequest.getEndTime())
                .couponStatus(CouponStatus.ACTIVE)        //상태 코드 (활성화, 비활성화)
                .remain(couponRequest.getCouponAmount())  //남은 쿠폰은 초기 쿠폰 총량과 동일
                .registerTime(LocalDateTime.now())        //등록 일시
                .updateTime(LocalDateTime.now())          //수정 일시
                .build();
    }

    /* 쿠폰 잔여량 감소 */
    public void minusRemain() {
        if (this.remain.compareTo(BigDecimal.ZERO ) > 0) {
            this.remain = this.remain.subtract(BigDecimal.ONE);
        }
        else  {
            throw new CouponRemainZeroException("쿠폰 잔여량이 없습니다");
        }
    }
}
