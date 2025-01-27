package com.example.codingmall.User.Login;

import com.example.codingmall.User.Login.LoginDto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/info")
    @Operation(summary = "회원 정보 가져오기" , description = "마이페이지를 위한 회원 정보를 가져옵니다.")
    public ResponseEntity<UserInfo> getUserInfo (@AuthenticationPrincipal UserDetails userDetails){
        UserInfo userInfo = authService.getUserInfo(userDetails.getUsername());
        return ResponseEntity.status(userInfo.getStatusCode()).body(userInfo);

    }
}
