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

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
    private final UserService userService;

    @Override /* /public/** 으로 시작하는 URL이지만 어나니머스 요청, 즉 체인 필터를 거치고 있으므로, 명시적으로 public으로 시작하는 URL이 필터를 거치지 않도록 만들어야 합니다 */
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // "/public/**" 경로에서는 필터를 실행하지 않음
        String path = request.getRequestURI();
        System.out.println("NotFilter Path 확인 : " + path);
        return  path.startsWith("/error") || path.startsWith("/public") ||
                path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") ||
                path.startsWith("/v3/api-docs.yaml") || path.equals("/v3/api-docs/swagger-config") ||
                path.startsWith("/category") || path.startsWith("/signin");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWTAuthFilter 동작");
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) {
            System.out.println("Authorization 헤더 없음. 필터 통과");
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



