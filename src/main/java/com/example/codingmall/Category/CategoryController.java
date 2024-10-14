package com.example.codingmall.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("public/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // 카테고리 전체 조회
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<Category> categories = categoryService.findAllCategories();
        List<CategoryDto> categoryDto = categories.stream()
                .map(CategoryDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDto);
    }

    // 카테고리 id로 조회
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(CategoryDto.of(category));
    }

    // 카테고리 등록
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
        Category category = categoryDto.toEntity();
        Category saveCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(saveCategory);
    }
}
