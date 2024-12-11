package com.example.codingmall.User;

import com.example.codingmall.User.Login.LoginDto.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Admin {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin() {
        if (userRepository.findByRole(Role.ROLE_ADMIN).isEmpty()) {
            UserDto userDto = UserDto.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1234"))
                    .birth(LocalDate.parse("2024-01-01"))
                    .name("관리자")
                    .phoneNumber("000-0000-0000")
                    .build();
            User userEntity = userDto.toEntity();
            userEntity.setRole(Role.ROLE_ADMIN);
            userRepository.save(userEntity);
            System.out.println("기본 관리자 생성 완료");
        }
    }
}
