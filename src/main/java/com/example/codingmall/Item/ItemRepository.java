package com.example.codingmall.Item;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    @EntityGraph(attributePaths = {"category"})
    Optional<Item> findByProductName(String productName); //상품 이름으로 검색
    @EntityGraph(attributePaths = {"category"})
    Optional<Item> findById(Long id);
    @EntityGraph(attributePaths = {"category"})
    List<Item> findAll();
    List<Item> findAllByStatus(String status);
    List<Item> findByCategoryId(Long categoryId); // 카테고리 별로 제품 조회
    List<Item>findAllByOrderByLikesDesc(); // 찜하기 수가 높은 순으로 상품 조회
}
