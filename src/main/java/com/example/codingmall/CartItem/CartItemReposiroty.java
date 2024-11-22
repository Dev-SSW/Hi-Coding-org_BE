package com.example.codingmall.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemReposiroty extends JpaRepository<CartItem, Long> {
    default CartItem findCartItemById(Long id){
        return findById(id).orElseThrow(()-> new IllegalStateException("그러한 카트 아이템 정보가 없습니다." + id));
    }
}
