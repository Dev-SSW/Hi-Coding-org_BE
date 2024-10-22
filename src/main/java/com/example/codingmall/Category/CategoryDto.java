package com.example.codingmall.Category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Long parentId;

    private List<CategoryDto> subCategories = new ArrayList<>();  // 트리 응답에만 사용

    public CategoryDto(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public void setSubCategories(List<CategoryDto> subCategories) {
        this.subCategories = subCategories;
    }
}