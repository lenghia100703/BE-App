package com.mobileapp.backend.dtos.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginDto {
    private String email;
    private String password;
}
