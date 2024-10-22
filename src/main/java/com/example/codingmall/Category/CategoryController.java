package com.example.codingmall.Category;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("public/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

   @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
       CategoryDto category = categoryService.findCategoryById(id);
       return ResponseEntity.ok(category);
   }

    // Create the root category structure
    @GetMapping("/tree")
    public ResponseEntity<CategoryDto> getCategoryTree() {
        CategoryDto rootCategory = categoryService.createCategoryRoot();
        return ResponseEntity.ok(rootCategory);


    }
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(category);
    }
}
