package com.example.codingmall.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category findCategoryById(Long id){
        return findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 카테고리 입니다." + id));
    }
}
