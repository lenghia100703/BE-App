package com.mobileapp.backend.services;

import com.mobileapp.backend.security.JWTProvider;
import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.auth.AuthResponseDto;
import com.mobileapp.backend.dtos.auth.LoginDto;
import com.mobileapp.backend.dtos.auth.RegisterDto;
import com.mobileapp.backend.dtos.user.UserInfoInToken;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.repositories.UserRepository;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.utils.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public CommonResponseDto<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<UserEntity> optionalUser = userRepository.findUserByEmail(loginDto.getEmail());

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            String accessToken = jwtProvider.generateAccessToken(new UserInfoInToken(loginDto.getEmail()));
            String refreshToken = jwtProvider.generateRefreshToken(new UserInfoInToken(loginDto.getEmail()));
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
            AuthResponseDto authResponse = new AuthResponseDto(user.getId(), accessToken, refreshToken);
            System.out.println(authResponse.getAccessToken());
            System.out.println(authResponse.getRefreshToken());
            return new CommonResponseDto<>(authResponse);
        } else {
            return new CommonResponseDto<>(ResponseCode.ERROR);
        }
    }

    public CommonResponseDto<String> logout() {
        SecurityContextHolder.clearContext();

        UserEntity currentUser = userRepository.findById(SecurityContextUtil.getCurrentUserId()).get();

        currentUser.setAccessToken(null);
        currentUser.setRefreshToken(null);
        userRepository.save(currentUser);
        return new CommonResponseDto<>("Logged out successfully");
    }

    public UserEntity register(@RequestBody RegisterDto registerDto) {
        if (findByEmail(registerDto.getEmail()) != null) {
            throw new CommonException(ResponseCode.ERROR, "Email đã tồn tại");
        }

        UserEntity user = new UserEntity();
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());

        return userRepository.save(user);
    }

    private UserEntity findByEmail(String email) {
        Optional<UserEntity> userOptional = userRepository.findUserByEmail(email);
        return userOptional.orElse(null);
    }
}
