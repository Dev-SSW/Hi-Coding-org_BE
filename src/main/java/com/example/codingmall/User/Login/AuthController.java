package com.example.codingmall.User.Login;

import com.example.codingmall.User.Login.LoginDto.JwtRequest;
import com.example.codingmall.User.Login.LoginDto.JwtResponse;
import com.example.codingmall.User.Login.LoginDto.SigninRequest;
import com.example.codingmall.User.Login.LoginDto.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<JwtResponse> signUp(@RequestBody SignupRequest signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public ResponseEntity<JwtResponse> signIn(@RequestBody SigninRequest signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    @Operation(summary = "리프레쉬 토큰 재생성")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody JwtRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
    @PostMapping("/validate")
    @Operation(summary = "토큰 유효성 검사")
    public ResponseEntity<JwtResponse> validateToken(@RequestBody JwtRequest validateTokenRequest){
        return ResponseEntity.ok(authService.validateToken(validateTokenRequest));
    }
}
