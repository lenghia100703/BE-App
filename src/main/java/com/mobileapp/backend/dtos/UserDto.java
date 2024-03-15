package com.mobileapp.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDto{
    private String userId;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String roleName;
}
