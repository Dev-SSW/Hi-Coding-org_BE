package com.example.codingmall.Login.LoginDto;

import com.example.codingmall.User.Role;
import com.example.codingmall.User.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupRequest {
    private String username;
    private String password;
    private int jumin;       // 주민등록번호
    private String name;     // 사용자 이름
    private String email;    // 이메일
    private String phoneNumber;// 휴대폰 번호
    private UserStatus status;   // 상태(탈퇴 회원 여부 파악)
    private Role role;       // 역할
}