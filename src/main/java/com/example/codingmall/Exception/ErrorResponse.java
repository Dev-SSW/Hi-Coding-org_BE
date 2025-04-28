package com.example.codingmall.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;
    private String error;
}
