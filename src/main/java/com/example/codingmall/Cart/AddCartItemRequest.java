package com.example.codingmall.Cart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCartItemRequest {
    private Long itemId;
    private int count;
}
