package com.example.codingmall.Login.Jwt;

import com.example.codingmall.User.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String jwtToken;
        final String username;
        final String authHeader = request.getHeader("Authorization");
        //헤더 값 Bearer 있는지 확인
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response); //요청을 다음 필터로 넘김, 인증 절차 X
            return;
        }
        jwtToken = authHeader.substring(7); //헤더 값 추출
        username = jwtUtils.extractUsername(jwtToken); //jwtUtils에서 사용자 이름 추출
        //사용자 이름이 null이 아니고, 현재 인증 컨텍스트에 인증 정보가 없는 경우
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            //유효성 검사
            if (jwtUtils.isTokenValid(jwtToken, userDetails)) { //JWT 서명과 만료 시간 확인
                //인증 정보를 나타내는 클래스
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                //추가 인증 세부 정보를 설정
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //컨텍스트로 이후에 요청에서 이 컨텍스트를 참조하여 인증하도록 처리함
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}



