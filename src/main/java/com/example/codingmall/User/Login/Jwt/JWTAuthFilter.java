package com.example.codingmall.User.Login.Jwt;

import com.example.codingmall.User.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
    private final UserService userService;

    // public/** 으로 시작하는 URL이지만 어나니머스 요청, 즉 체인 필터를 거치고 있으므로, 명시적으로 public으로 시작하는 URL이 필터를 거치지 않도록 만들어야 합니다
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println("ShouldNotFilter를 실행되었습니다. 이 다음에 JWTAuthFilter가 실행된다면 필터를 거치는 요청입니다.");
        String[] excludePath = {"/public", "/error", "/swagger-ui", "/v3/api-docs", "/v3/api-docs.yaml", "/v3/api-docs/swagger-config" };
        String path = request.getRequestURI();
        return  Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWTAuthFilter가 실행되었습니다. 이 다음에 Authorization 헤더 없음이 실행되지 않는다면 JWT 관련 요청입니다.");
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) {
            System.out.println("Authorization 헤더가 없어 기본적인 인증 필터만 통과합니다. (어나니머스 필터)");
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // JWT 인증 처리 로직
            String jwtToken = authHeader.substring(7);
            String username = jwtUtils.extractUsername(jwtToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}



