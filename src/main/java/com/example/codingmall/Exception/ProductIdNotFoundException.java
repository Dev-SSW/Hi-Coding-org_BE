package com.example.codingmall.Exception;

public class ProductIdNotFoundException extends RuntimeException{
    public ProductIdNotFoundException(String message) {
        super(message);
    }
}
