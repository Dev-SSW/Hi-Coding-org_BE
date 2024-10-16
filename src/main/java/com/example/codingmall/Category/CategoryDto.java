package com.example.codingmall.Category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private List<CategoryDto> children = new ArrayList<>();
    // 역직렬화를 위해 사용하는 @JsonCretor,@JsonProperty 추가.
    @JsonCreator
    public CategoryDto(@JsonProperty("id") Long id,
                       @JsonProperty("name") String name,
                       @JsonProperty("children") List<CategoryDto> children){
        this.id = id;
        this.name = name;
        this.children = children;
    }

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
