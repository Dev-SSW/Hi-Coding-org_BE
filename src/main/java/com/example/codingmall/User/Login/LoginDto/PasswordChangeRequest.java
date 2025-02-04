package com.example.codingmall.User.Login.LoginDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordChangeRequest {
    @NotBlank(message = "현재 비밀번호를 입력해주세요")
    private String currentPassword;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요")
    private String newPassword;
}
