package com.mobileapp.backend.dtos.user;

import com.mobileapp.backend.dtos.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String avatar;
}