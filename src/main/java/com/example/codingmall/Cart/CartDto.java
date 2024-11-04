package com.example.codingmall.Cart;

import com.example.codingmall.CartItem.CartItemDto;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> items;


    // cart 객체 -> cartDto 객체로 변환
    public  CartDto (Cart cart){
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.items = cart.getItems().stream().map(CartItemDto::new).collect(Collectors.toList());

    }

}
