package com.example.codingmall.Item;

import com.example.codingmall.Category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    private Category category;
    private String productName;
    private ItemStatus status;

    private long  stock;
    private long price;

    private String intro;
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private BigDecimal likes;

    @Builder
    public Item toEntity(){
        return Item.builder()
                .id(this.id)
                .category(category)
                .productName(this.productName)
                .status(this.status)
                .stock(this.stock)
                .price(this.price)
                .intro(this.intro)
                .content(this.content)
                .updateDate(LocalDateTime.now())
                .build();
    }
}
