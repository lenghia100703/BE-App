package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.auth.AuthResponseDto;
import com.mobileapp.backend.dtos.auth.LoginDto;
import com.mobileapp.backend.dtos.auth.RegisterDto;
import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.services.AuthService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public CommonResponseDto<UserDto> register(@RequestBody RegisterDto registerDto) {
        if (!Objects.equals(registerDto.getConfirmPassword(), registerDto.getPassword())) {
            throw new CommonException(ResponseCode.ERROR, "Mật khẩu nhắc lại không đúng!");
        }
        UserEntity newUser = authService.register(registerDto);
        return new CommonResponseDto<>(new UserDto(newUser));
    }

    @PostMapping("/login")
    public CommonResponseDto<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/logout")
    public CommonResponseDto<String> logout() {
        return authService.logout();
    }
}
