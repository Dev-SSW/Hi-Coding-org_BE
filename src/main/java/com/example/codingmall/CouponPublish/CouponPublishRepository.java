package com.example.codingmall.CouponPublish;

import com.example.codingmall.Exception.CouponNotFoundException;
import com.example.codingmall.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponPublishRepository extends JpaRepository<CouponPublish, Long> {
    /* 유저 이름으로 발급된 쿠폰들 찾기 */
    List<CouponPublish> findByUser(User user);
}
