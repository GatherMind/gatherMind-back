package woongjin.gatherMind.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import woongjin.gatherMind.exception.MissingTokenException;
import woongjin.gatherMind.exception.invalid.InvalidTokenException;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expirationTime;

    // 생성자 주입: SecretKey와 만료 시간 설정
    public JwtTokenProvider(@Value("${jwt.expiration}") long expirationTime) {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.expirationTime = expirationTime;
    }

    // 토큰 생성
    public String createToken(String memberId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    // 클레임 정보를 포함하여 토큰 생성 (예: 역할 및 닉네임)
    public String createTokenWithClaims(String memberId, String nickname, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(memberId)
                .claim("nickname", nickname)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    // 토큰에서 memberId 추출
    public String getMemberIdFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // 토큰에서 Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검증
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token has expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }

    // HTTP 요청 헤더에서 Bearer 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // HTTP 요청에서 토큰 유효성 검사 후 memberId 추출
    public String extractMemberIdFromRequest(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token == null) {
            throw new MissingTokenException("토큰이 요청 헤더에 없습니다.");
        }
        validateToken(token);
        return getMemberIdFromToken(token);
    }

    // 토큰에서 memberId를 직접 추출 (Deprecated된 메서드 수정)
    public String extractMemberIdFromToken(String token) {
        return getClaims(token).getSubject(); // subject에 memberId 저장
    }
}
