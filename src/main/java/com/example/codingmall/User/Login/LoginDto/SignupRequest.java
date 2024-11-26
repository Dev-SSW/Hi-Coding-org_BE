package com.example.codingmall.User.Login.LoginDto;

import com.example.codingmall.User.Role;
import com.example.codingmall.User.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupRequest {
    private String username;
    private String password;
    private int birth;       // 생년월일
    private String name;     // 사용자 이름
    private String phoneNumber;// 휴대폰 번호
}