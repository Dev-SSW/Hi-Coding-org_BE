// src/main/java/com/example/codingmall/Exception/UsernameAlreadyExistsException.java
package com.example.codingmall.Exception;
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}