package com.example.codingmall.User.Login.LoginDto;

import com.example.codingmall.User.Role;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserDto {
    private Long id;           // 회원고유번호
    private String username;   // 아이디
    private String password;   // 비밀번호
    private LocalDate birth;         // 주민등록번호
    private Role userRole; // 사용자 역할
    private String name;       // 사용자 이름
    private String phoneNumber;// 휴대폰 번호
    private String email;      // 이메일


    // UserDto -> User 변환 메서드
    public User toEntity() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .birth(this.birth)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .status(UserStatus.ACTIVATE)
                .role(this.userRole)
                .build();
    }
}
