package com.example.codingmall.User.Login;

import com.example.codingmall.Exception.UsernameAlreadyExistsException;
import com.example.codingmall.User.Login.Jwt.JWTUtils;
import com.example.codingmall.User.Login.LoginDto.*;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /* 회원 가입 */
    @Transactional
    public JwtResponse signUp(SignupRequest request) {
        try {
            userRepository.findByUsername(request.getUsername())
                    .ifPresent(u -> {throw new UsernameAlreadyExistsException("이미 존재하는 아이디입니다.");
                    });
            UserDto userDto = UserDto.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .birth(request.getBirth())
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            User userEntity = userDto.toEntity();
            User saveUser = userRepository.save(userEntity);

            if (saveUser != null && saveUser.getId() > 0) {
                return JwtResponse.builder()
                        .message("회원 저장 성공")
                        .statusCode(200)
                        .build();
            }
        } catch (Exception e) {
            return JwtResponse.builder()
                    .statusCode(500)
                    .error(e.getMessage())
                    .build();
        }
        return JwtResponse.builder()
                .statusCode(500)
                .message("회원 저장 실패")
                .build();
    }

    /* 로그인 */
    public JwtResponse signIn(SigninRequest signinRequest) {
        try {
            // 사용자 인증
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword())
            );
            // 사용자 정보 조회
            User user = userRepository.findUserByUsername(signinRequest.getUsername());

            // JWT 생성
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            return JwtResponse.builder()
                    .token(jwt)
                    .refreshToken(refreshToken)
                    .expirationTime("24Hr")
                    .message("로그인 성공")
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            return JwtResponse.builder()
                    .statusCode(500)
                    .error(e.getMessage())
                    .build();
        }
    }

    /* 리프레쉬 토큰 */
    public JwtResponse refreshToken(JwtRequest refreshTokenRequest) {
        try {
            String ourName = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            User users = userRepository.findUserByUsername(ourName);

            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                return JwtResponse.builder()
                        .statusCode(200)
                        .token(jwt)
                        .refreshToken(refreshTokenRequest.getToken())
                        .expirationTime("24Hr")
                        .message("재발급 성공")
                        .build();
            } else {
                return JwtResponse.builder()
                        .statusCode(401)
                        .error("유효하지 않은 토큰")
                        .build();
            }
        } catch (Exception e) {
            return JwtResponse.builder()
                    .statusCode(500)
                    .error(e.getMessage())
                    .build();
        }
    }

    /* 토큰 유효성 검사 */
    public JwtResponse validateToken(JwtRequest validateTokenRequest) {;
        try {
            String ourName = jwtUtils.extractUsername(validateTokenRequest.getToken());
            User user = userRepository.findUserByUsername(ourName);

            // 토큰 유효성 검사
            if (jwtUtils.isTokenValid(validateTokenRequest.getToken(), user)) {
                return JwtResponse.builder()
                        .statusCode(200)
                        .message("토큰이 유효합니다.")
                        .build();
            } else {
                return JwtResponse.builder()
                        .statusCode(401)
                        .message("유효하지 않은 토큰입니다.")
                        .build();
            }
        } catch (Exception e) {
            return JwtResponse.builder()
                    .statusCode(500)
                    .error(e.getMessage())
                    .build();
        }
    }
    @Transactional(readOnly = true)
    public UserInfo getUserInfo(String username){
        try{
            User user = userRepository.findUserByUsername(username);

            UserDto userDto = UserDto.builder()
                    .username(user.getUsername())
                    .birth(user.getBirth())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
            return UserInfo.builder()
                    .statusCode(200)
                    .message("회원 정보 불러오기 성공")
                    .userInfo(userDto)
                    .build();

        }catch (Exception e){
            return UserInfo.builder()
                    .statusCode(500)
                    .message(e.getMessage())
                    .build();
        }
    }
}
