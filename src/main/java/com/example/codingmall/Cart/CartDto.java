package com.example.codingmall.Cart;

import com.example.codingmall.CartItem.CartItemDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> items = new ArrayList<>();
    private int totalPrice; // 카트에 담긴 총 가격



    // cart 객체 -> cartDto 객체로 변환
    public  CartDto (Cart cart){
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.items = cart.getItems().stream()
                .map(CartItemDto::new)
                .collect(Collectors.toList());
        this.totalPrice = calculateTotalPrice();
    }
    public int calculateTotalPrice(){
        return items.stream()
                .mapToInt(item -> item.getPrice() * item.getCount())
                .sum();
    }

}
