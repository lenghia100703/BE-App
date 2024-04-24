package com.mobileapp.backend.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mobileapp.backend.configs.CustomAuthentication;
import com.mobileapp.backend.configs.CustomUserDetailsService;
import com.mobileapp.backend.dtos.user.UserInfoInToken;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = null;
        try {
            if (request.getHeader("jwt") != null) {
                authentication = getAuthenticationFromJwt(request.getHeader("jwt"));
            }
        } catch (Exception ignored) {
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private Authentication getAuthenticationInHeader(HttpServletRequest request, HttpServletResponse res) {
        String jwtHeader = request.getHeader("jwt");
        if (jwtHeader != null) {
            try {
                return getAuthenticationFromJwt(jwtHeader);
            } catch (TokenExpiredException e) {
                String jwtRefreshHeader = request.getHeader("jwt-refresh");
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

    public static Collection<? extends GrantedAuthority> convertStringToAuthorities(String authoritiesAsString){
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(authoritiesAsString));

        return authorities;
    }
}
