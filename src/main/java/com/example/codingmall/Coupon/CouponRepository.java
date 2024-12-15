package com.example.codingmall.Coupon;

import com.example.codingmall.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    default Coupon findCouponById(Long couponId) {
        return findById(couponId).orElseThrow(() -> new IllegalStateException("존재하지 않는 쿠폰 ID 입니다 : " + couponId));
    }

    /* 쿠폰 이름으로 쿠폰 찾기 */
    Optional<Coupon> findByCouponName(String name);

    default Coupon findCouponByCouponName(String name) {
        return findByCouponName(name).orElseThrow(() -> new UsernameNotFoundException("쿠폰 이름에 일치하는 쿠폰이 없습니다. : " + name));
    }
}
