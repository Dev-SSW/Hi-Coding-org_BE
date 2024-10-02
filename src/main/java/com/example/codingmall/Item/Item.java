package com.example.codingmall.Item;

import com.example.codingmall.Category.Category;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Item")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id; // 상품고유번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @Column(nullable = false)
    private String productName; // 상품명

    @Enumerated(EnumType.STRING)
    private Status status; // 상태코드

    private BigDecimal stock; // 재고
    private BigDecimal price; //가격

    private String intro; //소개글
    private String content; //상품 상세 설명

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private BigDecimal likes; // 좋아요 수

    public enum Status{
        AVAILABLE,
        UNAVAILABLE
    }
}
