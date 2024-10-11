package com.example.codingmall.Item;

import com.example.codingmall.Category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private ItemStatus status; // 상태코드

    private long stock; // 재고
    private long  price; //가격

    private String intro; //소개글
    private String content; //상품 상세 설명

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private BigDecimal likes; // 좋아요 수

    public void addStock(int quantity){
        this.stock += quantity;
    }
    public void removeStock(int quantity){
        long restStock = this.stock - quantity;
        if (restStock <0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stock = restStock;
    }

    // 상품 수정
    public void updateItem(ItemDto itemDto){
        this.category = itemDto.getCategory();
        this.productName = itemDto.getProductName();
        this.status = itemDto.getStatus();
        this.price = itemDto.getPrice();
        this.intro = itemDto.getIntro();
        this.content= itemDto.getContent();
        this.updateDate= LocalDateTime.now();
    }
}
