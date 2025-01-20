package com.example.codingmall.Exception;

public class ProductNameNotFoundException extends RuntimeException {
    public ProductNameNotFoundException(String message) {
        super(message);
    }
}
