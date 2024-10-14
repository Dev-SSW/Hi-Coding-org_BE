package com.example.codingmall.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Transactional(readOnly = true)
    public Category findCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Invalid category ID"));
    }

    public List<Category> findAllCategories() {
       return  categoryRepository.findAll();
    }
    // 카테고리 등록
    @Transactional
    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }
}
