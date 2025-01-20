package com.example.codingmall.Exception;

public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException(String message){
        super(message);
    }
}
