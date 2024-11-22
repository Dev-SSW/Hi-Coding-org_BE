package com.example.codingmall.Item;

import com.example.codingmall.Category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
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

    private int stock; // 재고
    private int  price; //가격

    private String intro; //소개글
    @Lob
    private byte[] content; // 제품 상세 설명 이미지

    private final LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate;
    private int likes; // 좋아요 수

    public void addStock(int quantity){
        this.stock += quantity;
    }
    public void removeStock(int quantity){
        int restStock = this.stock - quantity;
        if (restStock <0){
            throw new NotEnoughStockException("주문하려는 상품이 가지고 있는 상품보다 더 많습니다.");
        }
        this.stock = restStock;
    }

    // 좋아요 +1
    public void addLike(){
        this.likes += 1;
    }
    //좋아요 -1
    public void minusLike(){
        this.likes -=1;
    }
    // 상품 수정
    // Item 엔티티의 상품 수정 메서드
    public void updateItem(ItemDto itemDto) {
        if (itemDto.getCategory() != null) {
            this.category = itemDto.getCategory();
        }
        if (itemDto.getProductName() != null) {
            this.productName = itemDto.getProductName();
        }
        if (itemDto.getStatus() != null) {
            this.status = itemDto.getStatus();
        }
        if (itemDto.getPrice() >= 0) {
            this.price = itemDto.getPrice();
        }
        if (itemDto.getIntro() != null) {
            this.intro = itemDto.getIntro();
        }
        if (itemDto.getContent() != null) {
            this.content = itemDto.getContent();
        }
        if(itemDto.getStock() >=0){
            this.stock = itemDto.getStock();
        }
        this.updateDate = LocalDateTime.now(); // 수정 시간 업데이트
    }
    public void setCategory(Category category){
        this.category = category;
    }

}
