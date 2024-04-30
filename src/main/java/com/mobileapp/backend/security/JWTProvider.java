package com.mobileapp.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mobileapp.backend.constants.JWTConstant;
import com.mobileapp.backend.dtos.user.UserInfoInToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTProvider {
    private SecretKey Key;

    @Value("${application.security.jwt.secret-key}")
    private String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";

    public JWTProvider() {
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateAccessToken(HttpServletResponse res, UserInfoInToken userInfoInToken, String role) {
        Long id = userInfoInToken.getId();
        int accessTokenExpiration = JWTConstant.ACCESS_TOKEN_EXPIRED;
        String accessToken = doGenerateToken(id, role, accessTokenExpiration);
        ResponseCookie tokenCookie = ResponseCookie.from(JWTConstant.COOKIE_ACCESS_TOKEN, accessToken)
                .maxAge(7 * 24 * 60 * 60)
                .httpOnly(true).path("/").secure(true).sameSite("None").build();
        res.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());
        res.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        res.addHeader(JWTConstant.HEADER_ACCESS_TOKEN, accessToken);

        return accessToken;
    }

    public String generateRefreshToken(HttpServletResponse res, UserInfoInToken userInfoInToken, String role) {
        Long id = userInfoInToken.getId();
        int refreshTokenExpiration = JWTConstant.REFRESH_TOKEN_EXPIRED;
        String refreshToken = doGenerateToken(id, role, refreshTokenExpiration);
        ResponseCookie tokenCookie = ResponseCookie.from(JWTConstant.COOKIE_REFRESH_TOKEN, refreshToken)
                .maxAge(7 * 24 * 60 * 60)
                .httpOnly(true).path("/").secure(true).sameSite("None").build();
        res.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());
        res.addHeader(JWTConstant.HEADER_REFRESH_TOKEN, refreshToken);

        return refreshToken;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect", ex.fillInStackTrace());
        }
    }

    private String doGenerateToken(Long id, String role, int expiration) {
        return Jwts.builder()
                .claim("ID", id)
                .claim("AUTHORITIES", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Key)
                .compact();
    }

    public DecodedJWT decodeJwt(String value) {
        return JWT.decode(value);
    }
}
