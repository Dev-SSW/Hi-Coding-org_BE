package com.example.codingmall.CouponPublish;

import com.example.codingmall.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponPublishService {
    private final CouponPublishRepository couponPublishRepository;

    /* 유저의 아이디를 통해 발급된 쿠폰 찾기 */
    public List<CouponPublish> getCouponPublish(User user) {
        return couponPublishRepository.findByUser(user);
    }
}
