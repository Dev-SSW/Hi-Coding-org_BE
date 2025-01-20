package com.example.codingmall.Coupon;

import com.example.codingmall.CouponPublish.CouponPublish;
import com.example.codingmall.CouponPublish.CouponPublishRepository;
import com.example.codingmall.Exception.CouponRemainZeroException;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponPublishRepository couponPublishRepository;
    private final UserRepository userRepository;

    /* 쿠폰 만들기 */
    @Transactional
    public void couponCreate(User user, CouponRequest couponRequest) {
        Coupon coupon = Coupon.createCoupon(user, couponRequest);
        couponRepository.save(coupon);
    }

    /* 특정 유저에게 쿠폰 발급 */
    @Transactional
    public void couponPublish(User user, Long couponId) {
        Coupon coupon = couponRepository.findCouponById(couponId);
        // 쿠폰 잔여량 확인
        if (coupon.getRemain().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CouponRemainZeroException("쿠폰이 다 떨어졌습니다.");
        }
        CouponPublish couponPublish = CouponPublish.couponPublishToUser(user , coupon);
        couponPublishRepository.save(couponPublish);
    }

    /* 생일 쿠폰 자동 발급 */
    @Transactional
    public void publishBirthCouponAuto() {
        List<User> birthDayUsers = userRepository.findUsersWithTodayBirthday();
        Coupon birthdayCoupon = couponRepository.findCouponByCouponName("생일 축하 쿠폰");
        for (User user : birthDayUsers) {
            if (birthdayCoupon.getRemain().compareTo(BigDecimal.ZERO) > 0) {
                CouponPublish couponPublish = CouponPublish.couponPublishToUser(user, birthdayCoupon);
                couponPublishRepository.save(couponPublish);
            }
        }
    }
}
