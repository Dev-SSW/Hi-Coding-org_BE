package com.example.codingmall.CouponPublish;

import com.example.codingmall.Coupon.Coupon;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Coupon_publish")
public class CouponPublish {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponpublish_id")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private Status status;
    private LocalDateTime useDate;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private enum Status{
        available,unavailable
    }
}

