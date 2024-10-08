package com.example.codingmall.Cart;

import com.example.codingmall.Item.Item;
import com.example.codingmall.User.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id",nullable = false)
    private Item item;

    private int count; // 상품 수량
    private int price; // 상품 가격
    private LocalDateTime registerDate;// 등록일시
    private LocalDateTime updateDate; //수정일시
}
