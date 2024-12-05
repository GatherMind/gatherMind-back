package woongjin.gatherMind.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}") // application.properties 또는 application.yml에서 설정된 값
    private String secretKey;

    @Value("${jwt.expiration}") // 토큰 만료 시간 (밀리초)
    private long expirationTime;

    // SecretKey 생성
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 토큰 생성
    public String generateToken(String memberId) {
        return Jwts.builder()
                .setSubject(memberId) // 토큰에 사용자 ID 설정
                .setIssuedAt(new Date()) // 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // SecretKey와 알고리즘 설정
                .compact();
    }

    // JWT 토큰에서 사용자 ID 추출
    public String verifyTokenAndGetMemberId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // SecretKey 설정
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();

            return claims.getSubject(); // 사용자 ID 반환
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.", e);
        }
    }

    // 토큰의 유효성을 검증
    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // SecretKey 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱
            return true; // 검증 성공 시 true 반환
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 검증 실패 시 false 반환
        }
    }
}
