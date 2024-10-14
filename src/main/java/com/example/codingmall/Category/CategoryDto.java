package com.example.codingmall.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private List<CategoryDto> children;
    //컨트롤러에 전해주기 위한 category -> categoryDto 변환 클래스
    public static CategoryDto of(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getChildren().stream().map(CategoryDto::of).collect(Collectors.toList())
        );

    }
    public Category toEntity(){
        return new Category(this.name,null,LocalDateTime.now());
    }
}
