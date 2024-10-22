package com.example.codingmall.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /* ID로 카테고리 찾기 */
    public CategoryDto findCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Invalid category ID"));
        return new CategoryDto(category.getId(), category.getName(), category.getParentId());
    }

    /* 트리 구조 생성 */
    @Transactional
    public CategoryDto createCategoryRoot() {
        List<Category> allCategories = categoryRepository.findAll();

        // 부모 ID로 그룹화
        Map<Long, List<CategoryDto>> groupedByParentId = allCategories.stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getParentId()))
                .collect(Collectors.groupingBy(dto -> dto.getParentId() != null ? dto.getParentId() : 0L));

        // 루트 노드를 생성 (ID가 0L인 노드)
        CategoryDto rootCategory = new CategoryDto(0L, "ROOT", null);
        addSubCategories(rootCategory, groupedByParentId);
        return rootCategory;
    }

    /* 하위 카테고리를 재귀적으로 추가 */
    private void addSubCategories(CategoryDto parent, Map<Long, List<CategoryDto>> groupByParentId) {
        List<CategoryDto> subCategories = groupByParentId.get(parent.getId());

        if (subCategories != null) {
            parent.setSubCategories(subCategories);
            for (CategoryDto subCategory : subCategories) {
                addSubCategories(subCategory, groupByParentId);
            }
        }
    }

    /* 카테고리 생성 */
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getName(), categoryDto.getParentId());
        Category savedCategory = categoryRepository.save(category);

        return new CategoryDto(savedCategory.getId(), savedCategory.getName(), savedCategory.getParentId());
    }
}
