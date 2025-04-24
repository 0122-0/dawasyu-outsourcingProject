package com.example.dawasyu.common.jwt;

import com.example.dawasyu.common.jwt.dto.JwtDto;
import com.example.dawasyu.domain.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtDto generateToken(User user) {

        String authorities = user.getUserRole().name();

        System.out.println("=== JWT 생성 시 권한 === " + user.getUserRole());

        long now = (new Date()).getTime();

        Date accessTokenExpiration = new Date(now + 1000 * 60 * 30); // 현재시간 + 30분

        String accessToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiration)
                .signWith(key)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();

        return JwtDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();

        Long userId = Long.parseLong(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userId, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("invalid JWT", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    // accessToken
    private Claims parseClaims(String accessToken){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // accessToken에서 userId 꺼내는 메서드
    public Long getUserIdFromToken(String token) {
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    // 토큰 꺼내기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 만료 시간 가져오기 (블랙리스트용)
    public long getExpiration(String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration().getTime() - new Date().getTime();
    }



}
