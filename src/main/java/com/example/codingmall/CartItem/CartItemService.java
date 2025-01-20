package com.example.codingmall.CartItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItem findCartItemById(Long id){
        CartItem cartItem = cartItemRepository.findCartItemById(id);
        return cartItem;
    }
    @Transactional
    // 장바구니 안 갯수를 바꾸기
    public CartItemDto updateCartItemCount (Long id, int newCount){
        CartItem cartItem =findCartItemById(id);
        cartItem.updateCount(newCount);
        cartItemRepository.save(cartItem);
        return new CartItemDto(cartItem);
    }

    @Transactional
    // 장바구니 안 갯수 삭제
    public void removeCartItem(Long id){
        CartItem cartItem = findCartItemById(id);
        cartItemRepository.delete(cartItem);
    }
}
