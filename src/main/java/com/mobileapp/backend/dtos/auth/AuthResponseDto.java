package com.mobileapp.backend.dtos.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";
}
