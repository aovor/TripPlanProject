package com.TripPlan.TripPlanProject.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class JwtTokenProvider {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간
    private final Key signingKey;

    public JwtTokenProvider() {
        String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            log.error("JWT_SECRET_KEY is missing or empty");
            throw new IllegalArgumentException("JWT_SECRET_KEY environment variable is not set or empty");
        }

        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        this.signingKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    // JWT 토큰 생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("issuedAt", System.currentTimeMillis());
        claims.put("random", UUID.randomUUID().toString());
        Date now = new Date();
        Date validity = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(signingKey, SignatureAlgorithm.HS512)  // SecretKey 사용
                .compact();
    }

    // JWT 토큰에서 사용자 정보 추출
    public String getUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token");
            throw new IllegalArgumentException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token");
            throw new IllegalArgumentException("Unsupported JWT token");
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token");
            throw new IllegalArgumentException("Invalid JWT token");
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty");
            throw new IllegalArgumentException("JWT claims string is empty");
        }
    }

    // JWT 토큰 유효성 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token");
            return false;
        }
    }
}
