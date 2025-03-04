package com.example.codingmall.User.Login;

import com.example.codingmall.User.Login.LoginDto.*;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/user")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/public/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<JwtResponse> signUp(@RequestBody SignupRequest signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/public/signin")
    @Operation(summary = "로그인")
    public ResponseEntity<JwtResponse> signIn(@RequestBody SigninRequest signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    @Operation(summary = "리프레쉬 토큰 재생성")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody JwtRequest refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
    @PostMapping("/public/validate")
    @Operation(summary = "토큰 유효성 검사")
    public ResponseEntity<JwtResponse> validateToken(@RequestBody JwtRequest validateTokenRequest){
        return ResponseEntity.ok(authService.validateToken(validateTokenRequest));
    }
    @GetMapping("/user/info")
    //@PreAuthorize("hasAnyRole('USER','SOCIAL','ADMIN')") // 인증된 사용자만 호출 가능
    @Operation(summary = "회원 정보 가져오기" , description = "마이페이지를 위한 회원 정보를 가져옵니다.")
    public ResponseEntity<UserInfo> getUserInfo (@AuthenticationPrincipal User user){
        UserInfo userInfo = userService.getUserInfo(user);
        return ResponseEntity.status(userInfo.getStatusCode()).body(userInfo);
    }
    @PutMapping("/user/changePassword")
    //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_SOCIAL','ROLE_ADMIN')") // 인증된 사용자만 호출 가능
    @Operation(summary = "비밀번호 변경하기", description = "현재 사용자의 비밀번호와 입력한 비밀번호가 맞는지 비교한 후, 비밀번호를 변경합니다.")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody PasswordChangeRequest request, @AuthenticationPrincipal User user){
        ResponseDto dto = userService.changePassword(request, user);
        return ResponseEntity.status(dto.getStatusCode()).body(dto);
    }
}
