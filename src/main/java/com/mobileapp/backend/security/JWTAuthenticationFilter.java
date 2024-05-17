package com.mobileapp.backend.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mobileapp.backend.configs.CustomAuthentication;
import com.mobileapp.backend.configs.CustomUserDetailsService;
import com.mobileapp.backend.constants.JWTConstant;
import com.mobileapp.backend.dtos.user.UserInfoInToken;
import com.mobileapp.backend.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;

    public static Collection<? extends GrantedAuthority> convertStringToAuthorities(String authoritiesAsString) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(authoritiesAsString));

        return authorities;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = null;
        try {
            String token = request.getHeader(HttpHeaders.COOKIE);
            String accessToken = token.split("=")[1].split("; ")[0];

            if (accessToken != null) {
                authentication = getAuthenticationFromJwt(accessToken);
            }
        } catch (Exception ignored) {
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private Authentication getAuthenticationInHeader(HttpServletRequest request, HttpServletResponse res) {
        String jwtHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtHeader != null) {
            try {
                return getAuthenticationFromJwt(jwtHeader);
            } catch (TokenExpiredException e) {
                String jwtRefreshHeader = request.getHeader(JWTConstant.HEADER_REFRESH_TOKEN);
                if (jwtRefreshHeader != null) {
                    return getUserIdFromJwtRefresh(jwtRefreshHeader, res);
                }
            }
        }

        return null;
    }

    private Authentication getAuthenticationFromJwt(String value) {
        DecodedJWT decodedJWT = jwtProvider.decodeJwt(value);
        Long userId = decodedJWT.getClaim("ID").asLong();
        System.out.println(userId);
        String authority = decodedJWT.getClaim("AUTHORITIES").asString();

        Collection<? extends GrantedAuthority> authorities = convertStringToAuthorities(authority);
        return new CustomAuthentication(userId, authorities);
    }

    private Authentication getUserIdFromJwtRefresh(String value, HttpServletResponse res) {
        DecodedJWT decodedJWT = jwtProvider.decodeJwt(value);
        Long userId = decodedJWT.getClaim("ID").asLong();
        String authority = decodedJWT.getClaim("AUTHORITIES").asString();

        Collection<? extends GrantedAuthority> authorities = convertStringToAuthorities(authority);

        jwtProvider.generateAccessToken(res, new UserInfoInToken(userId), authority);
        jwtProvider.generateRefreshToken(res, new UserInfoInToken(userId), authority);
        return new CustomAuthentication(userId, authorities);
    }
}
