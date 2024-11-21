package com.example.codingmall.Category;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.Hibernate.map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    @PostConstruct
    public void createDefaultCategory(){
        if(!categoryRepository.existsById(0L)){
            categoryRepository.save(Category.defaultBuilder().build());
        }
    }

    // 카테고리 생성
   @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto){
       Category category = categoryDto.toEntity();
       Category savedCategory = categoryRepository.save(category);
       return CategoryDto.from(savedCategory);
   }

   // 카테고리 id로 조회
    public CategoryDto getCategoryById(Long id){
        Category category = categoryRepository.findCategoryById(id);
        return CategoryDto.from(category);
    }

    public List<CategoryDto> getAllCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(CategoryDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCategory(Long id){
       categoryRepository.deleteById(id);
    }
}
