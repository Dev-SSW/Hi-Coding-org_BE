package com.example.codingmall.CartItem;

import lombok.Getter;

@Getter
public class CartItemDto {
    private Long itemId;

    private String itemName;
    private int count;
    private int price;

    public CartItemDto (CartItem cartItem){
        this.itemId = cartItem.getItem().getId();
        this.itemName = cartItem.getItem().getProductName();

        this.count = cartItem.getCount();
        this.price =cartItem.getPrice();

    }
}
