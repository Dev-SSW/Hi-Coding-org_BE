package com.example.codingmall.CartItem;

import com.example.codingmall.Exception.CartItemNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    default CartItem findCartItemById(Long id){
        return findById(id).orElseThrow(()-> new CartItemNotFoundException(id + "에 대한 카트 아이템 정보가 없습니다."));
    }
}
