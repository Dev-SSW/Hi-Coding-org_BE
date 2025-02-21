package com.example.codingmall.User.Login.LoginDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto {
    private int statusCode;
    private String message;
    private String error;
}
