package com.example.codingmall.Exception;

public class OrderIdNotFoundException extends RuntimeException{
    public OrderIdNotFoundException(String message) {
        super(message);
    }
}
