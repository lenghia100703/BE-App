package com.mobileapp.backend.services;

import com.mobileapp.backend.configs.JWTProvider;
import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.auth.AuthResponseDto;
import com.mobileapp.backend.dtos.auth.LoginDto;
import com.mobileapp.backend.dtos.user.UserInfoInToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    AuthenticationManager authenticationManager;

    @Autowired
    JWTProvider jwtProvider;

    public CommonResponseDto<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateAccessToken(new UserInfoInToken(loginDto.getEmail()));
        String refreshToken = jwtProvider.generateRefreshToken(new UserInfoInToken(loginDto.getEmail()));


        return null;
    }
}
