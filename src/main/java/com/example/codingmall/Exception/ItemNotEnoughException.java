package com.example.codingmall.Exception;

public class ItemNotEnoughException extends RuntimeException {
    public ItemNotEnoughException(String message) {
        super(message);
    }
}
