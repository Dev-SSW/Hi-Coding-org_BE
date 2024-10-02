package com.example.codingmall.Category;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name =  "Category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id; // 카테고리 식별번호

    private BigDecimal category1;
    private BigDecimal category2;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
