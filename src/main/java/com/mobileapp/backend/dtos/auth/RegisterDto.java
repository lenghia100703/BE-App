package com.mobileapp.backend.dtos.auth;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
