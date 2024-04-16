package com.mobileapp.backend.dtos.user;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String password;
    private String confirmPassword;
}
