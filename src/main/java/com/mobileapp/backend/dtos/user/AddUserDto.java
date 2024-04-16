package com.mobileapp.backend.dtos.user;

import lombok.Data;

@Data
public class AddUserDto {
    private String username;
    private String phone;
    private String email;
    private String password;
}
