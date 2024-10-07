package com.example.codingmall.Login.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {
    private SecretKey key;
    private static final long EXPIRATION_TIME = 86400000; // 24 hours or 86400000 milliseconds
    private static final long REFRESH_EXPIRATION_TIME = 604800000; // 7 days

    @Value("${spring.jwt.secret}")
    private String secretString;

    @PostConstruct //HmacSHA256
    public void init() {
        this.key = new SecretKeySpec(secretString.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())              //토큰의 주체 설정
                .setIssuedAt(new Date(System.currentTimeMillis()))  //토큰 생성 시점 설정
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  //토큰의 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)    //HMAC SHA256 알고리즘을 사용하여 비밀 키(key)로 토큰을 서명
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)  //추가적인 정보 설정, (역할, 권한 등)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //사용자 이름 추출 (위에서 subject로 사용자 이름을 설정했음)
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    //JWT에서 특정 클레임(정보)을 추출하는 공통 메서드
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        //JWT를 파싱하고, 그 내용(body)에서 클레임 정보를 추출
        return claimsResolver.apply(claims); //클레임에서 필요한 특정 정보를 추출하는 람다 함수를 적용
    }

    //토큰 유효성 검사
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //토큰 만료 검사 (getExpiration을 통해 만료 시간을 가져오고, 현재 시각과 비교)
    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
