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
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    //디폴트 카테고리 생성
    /*
    @PostConstruct
    public void initDefaultCategory() {
        categoryService.createDefaultCategory();
    }
    */

    @Operation(summary = "카테고리 생성",description = "새로운 카테고리를 생성합니다.")
    @PostMapping("admin/category/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(createdCategory);
    }

    @Operation(summary = "카테고리 정보 조회",description = "카테고리 정보를 조회합니다.")
    @GetMapping("admin/category/getInfo/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name = "categoryId") Long categoryId) {
        CategoryDto category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "모든 카테고리 조회",description = "현재 모든 카터고리를 조회합니다.")
    @GetMapping("admin/category/search")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @Operation(summary = "특정 카테고리 삭제",description = "특정 카터고리를 삭제합니다.")
    @DeleteMapping("admin/category/deleteCategory/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategories(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
