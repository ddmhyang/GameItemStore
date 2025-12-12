package com.example.GameItemStore.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // 1. 암호화 키 (실무에선 비밀로 해야 함)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 2. 토큰 생성 메서드
    public String generateToken(Long memberId, String nickname) {
        return Jwts.builder()
                .setSubject(String.valueOf(memberId)) // 토큰 주인(ID)
                .claim("nickname", nickname)          // 추가 정보
                .setIssuedAt(new Date())              // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 유효시간 10시간
                .signWith(SECRET_KEY)                 // 서명
                .compact();
    }
}