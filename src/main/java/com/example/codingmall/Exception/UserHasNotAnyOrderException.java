package com.example.codingmall.Exception;

public class UserHasNotAnyOrderException extends RuntimeException {
    public UserHasNotAnyOrderException(String message) {
        super(message);
    }
}
