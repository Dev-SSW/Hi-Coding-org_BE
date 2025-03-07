package com.example.codingmall.Item;

import com.example.codingmall.Exception.ProductIdNotFoundException;
import com.example.codingmall.Exception.ProductNameNotFoundException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    @EntityGraph(attributePaths = {"category"})
    Optional<Item> findItem_ByProductName(String productName);
    // 상품 이름으로 검색
    default Item findItemByProductName(String productName){
        return findItem_ByProductName(productName).orElseThrow(()->new ProductNameNotFoundException("그러한 제품 이름이 없습니다.: " +productName));
    }

    default Item findItemByItemId(Long itemId){
        return findById(itemId).orElseThrow(()->new ProductIdNotFoundException("그러한 제품 아이디가 없습니다 : " +itemId));
    }
    @EntityGraph(attributePaths = {"category"})
    List<Item> findAll();
    List<Item> findAllByStatus(String status);
//    List<Item> findByCategoryId(Long categoryId); // 카테고리 별로 제품 조회
    List<Item>findAllByOrderByLikesDesc(); // 찜하기 수가 높은 순으로 상품 조회

}
