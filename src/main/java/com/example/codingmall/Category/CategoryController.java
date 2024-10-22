package com.example.codingmall.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /* ID로 카테고리 찾기 */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Long categoryId){
       CategoryDto category = categoryService.findCategoryById(categoryId);
       return ResponseEntity.ok(category);
    }
    /* 카테고리 트리 조회 */
    @GetMapping("/tree")
    public ResponseEntity<CategoryDto> getCategoryTree() {
        CategoryDto rootCategory = categoryService.createCategoryRoot();
        return ResponseEntity.ok(rootCategory);
    }
    /* 카테고리 생성 */
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(category);
    }
}
