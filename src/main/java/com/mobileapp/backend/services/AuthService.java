package com.mobileapp.backend.services;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.auth.AuthResponseDto;
import com.mobileapp.backend.dtos.auth.LoginDto;
import com.mobileapp.backend.dtos.auth.RegisterDto;
import com.mobileapp.backend.dtos.user.UserInfoInToken;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.UserRepository;
import com.mobileapp.backend.security.JWTProvider;
import com.mobileapp.backend.utils.SecurityContextUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Service
public class AuthService {
    private final HttpServletResponse response;
    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager, HttpServletResponse response) {
        this.authenticationManager = authenticationManager;
        this.response = response;
    }

    public CommonResponseDto<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        Optional<UserEntity> optionalUser = userRepository.findUserByEmail(loginDto.getEmail());

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            String accessToken = jwtProvider.generateAccessToken(response, new UserInfoInToken(user.getId()), user.getRole());
            String refreshToken = jwtProvider.generateRefreshToken(response, new UserInfoInToken(user.getId()), user.getRole());
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            AuthResponseDto authResponse = new AuthResponseDto(user.getId(), accessToken, refreshToken);
            userRepository.save(user);
            return new CommonResponseDto<>(authResponse);
        } else {
            return new CommonResponseDto<>(ResponseCode.ERROR);
        }
    }

    public CommonResponseDto<String> logout() {
        Long id = SecurityContextUtil.getCurrentUserId();
        UserEntity currentUser = userRepository.findById(id).get();
        currentUser.setAccessToken(null);
        currentUser.setRefreshToken(null);
        userRepository.save(currentUser);
        SecurityContextHolder.clearContext();

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", null)
                .maxAge(1000)
                .httpOnly(true).path("/").secure(true).sameSite("None").build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        ResponseCookie jwtRefreshCookie = ResponseCookie.from("jwt-refresh", null)
                .maxAge(1000)
                .httpOnly(true).path("/").secure(true).sameSite("None").build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString());

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
