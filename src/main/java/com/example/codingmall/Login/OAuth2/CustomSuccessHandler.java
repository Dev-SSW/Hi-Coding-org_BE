package com.example.codingmall.Login.OAuth2;

import com.example.codingmall.Login.Jwt.JWTUtils;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
    //http://localhost:8080/oauth2/authorization/google
    //http://localhost:8080/oauth2/authorization/naver
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // OAuth2User로 캐스팅
        User customUser = (User) authentication.getPrincipal();

        String username = customUser.getUsername();

        // JWT 및 Refresh Token 처리
        String accessToken = jwtUtils.generateToken(customUser);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), customUser);

        // JWT를 헤더에 추가
        response.addHeader("Authorization", "Bearer " + accessToken);

        // Refresh Token을 쿠키에 추가
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);  // XSS 공격 방지
        refreshTokenCookie.setSecure(true);  // HTTPS에서만 전송
        refreshTokenCookie.setPath("/");  // 전체 경로에서 사용 가능
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);  // 7일 동안 유효
        response.addCookie(refreshTokenCookie);

        // 로그인 성공 후 리다이렉트할 URL 결정
        //String targetUrl = determineTargetUrl(request, response);

        // 리다이렉트 처리
        //getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
