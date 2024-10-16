package com.example.codingmall.Category;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // DTO로 변환하여 직렬화
    @Transactional(readOnly = true)
    public CategoryDto findCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Invalid category ID"));
        return new CategoryDto(category.getId(), category.getName(), category.getParentId());
    }

    @Transactional
    public CategoryDto createCategoryRoot() {
        Map<Long, List<CategoryDto>> groupByParent = categoryRepository.findAll()
                .stream()
                .map(ce -> new CategoryDto(ce.getParentId(), ce.getName(), ce.getParentId()))
                .collect(groupingBy(CategoryDto::getParentId));

        CategoryDto rootCategoryDto = new CategoryDto(1L, "ROOT", null);
        addSubCategories(rootCategoryDto, groupByParent);

        return rootCategoryDto;
    }

    private void addSubCategories(CategoryDto parent, Map<Long, List<CategoryDto>> groupByParentId) {
        // 1. parent의 키로, subcategories를 찾는다.
        List<CategoryDto> subCategories = groupByParentId.get(parent.getId());

        // 종료 조건.
        if (subCategories == null)
            return;

        // 2. sub categories 세팅
        parent.setSubCategories(subCategories);

        subCategories.stream()
                .forEach(s ->
                        addSubCategories(s, groupByParentId));
    }
    public CategoryDto createCategory (CategoryDto categoryDto){
        Category category = new Category(categoryDto.getName(), categoryDto.getParentId());
        Category savedCategory  = categoryRepository.save(category);

        return new CategoryDto(savedCategory.getId(),savedCategory.getName(),savedCategory.getParentId());

    }
}
