package com.example.codingmall.Item;

import com.example.codingmall.Category.Category;
import com.example.codingmall.Category.CategoryDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ItemDto {
    private Long id;
    private CategoryDto category;
    private String productName;
    private ItemStatus status;

    private int stock;
    private int price;

    private String intro;
    private String content; // 제품 상세 설명 이미지

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private int likes;

    public Item toEntity(){
        return Item.builder()
                .id(this.id)
                .category(category != null ?
                        Category.builder().categoryId(category.getId())
                                .name(category.getName()).build() : null)
                .productName(this.productName)
                .status(this.status)
                .stock(this.stock)
                .price(this.price)
                .intro(this.intro)
                .content(this.content != null ? this.content.getBytes() : null)
                .likes(this.likes)
                .updateDate(LocalDateTime.now())
                .build();
    }
    // 엔티티로부터 DTO변환
    public static ItemDto from(Item item){
        CategoryDto categoryDto
                = (item.getCategory() != null) ? CategoryDto.from(item.getCategory()) : null;
        return ItemDto.builder()
                .id(item.getId())
                .category(categoryDto)
                .productName(item.getProductName())
                .status(item.getStatus())
                .stock(item.getStock())
                .price(item.getPrice())
                .intro(item.getIntro())
                .content(item.getContent() != null ? new String(item.getContent()) : null)
                .likes(item.getLikes())
                .createDate(item.getCreateDate())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
