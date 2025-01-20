package com.example.codingmall.Exception;

import com.example.codingmall.CartItem.CartItem;

public class CategoryNotfoundException extends RuntimeException{
    public CategoryNotfoundException(String message){
        super(message);
    }
}
