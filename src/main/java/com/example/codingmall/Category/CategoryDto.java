package com.example.codingmall.Category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    @Column(name = "category_id")
    private Long id;
    private String name;

    // category --> categoryDto
    public static CategoryDto from(Category category){
        return CategoryDto.builder()
                .id(category.getCategoryId())
                .name(category.getName())
                .build();
    }
    public Category toEntity(){
        return Category.builder()
                .categoryId(this.getId())
                .name(this.getName())
                .build();
    }
}