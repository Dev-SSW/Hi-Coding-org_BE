package com.example.codingmall.User.Login.LoginDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserInfo {
    private int statusCode;
    private String message;
    private String error;
    private UserDto userInfo; // 회원정보를 담는 필드
}
