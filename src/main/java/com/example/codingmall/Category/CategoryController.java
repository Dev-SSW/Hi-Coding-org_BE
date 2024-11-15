package com.example.codingmall.Category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Category",description = "카테고리를 관리하는 Api")
@RestController
@RequestMapping("public/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostConstruct
    public void initDefaultCategory(){
        categoryService.createDefaultCategory();
    }
    @Operation(summary = "카테고리 생성",description = "새로운 카테고리를 생성합니다.")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(createdCategory);
    }
    @Operation(summary = "카테고리 정보 조회",description = "카테고리 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id){
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "모든 카테고리 조회",description = "현재 모든 카터고리를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @Operation(summary = "특정 카테고리 삭제",description = "특정 카터고리를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<CategoryDto> deleteCategories(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
