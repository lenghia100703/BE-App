package com.mobileapp.backend.security;

import com.mobileapp.backend.dtos.user.UserInfoInToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    private final String secretKey = "abc123";

    private final int accessTokenExpiration = 86400000;

    private final int refreshTokenExpiration = 604800000;

    public String generateAccessToken(UserInfoInToken userInfoInToken) {
        String subject = userInfoInToken.getEmail();

        return doGenerateToken(subject, accessTokenExpiration);
    }

    public String generateRefreshToken(UserInfoInToken userInfoInToken) {
        String subject = userInfoInToken.getEmail();

        return doGenerateToken(subject, refreshTokenExpiration);
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }

    private String doGenerateToken(String subject, int expiration) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
