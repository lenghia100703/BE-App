package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody UserDto userDto) {
        return "Register successfully";
    }
}
