package com.example.codingmall.Category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Long parentId;

    private List<CategoryDto> subCategories;

    public CategoryDto(Long id, String name,Long parentId ) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
    public void setSubCategories(List<CategoryDto> subCategories) {
        this.subCategories = subCategories;
    }
}