package com.example.codingmall.Exception;

public class SerialNumberNotFoundException extends RuntimeException{
    public SerialNumberNotFoundException(String message) {
        super(message);
    }
}
