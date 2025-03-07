package com.example.codingmall.Category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    private String name;

    // 초기 디폴트 카테고리 생성
    @Builder(builderMethodName = "defaultBuilder")
    public static Category DefaultCategory(){
        return new Category(0L,"Default Category");
    }
}
