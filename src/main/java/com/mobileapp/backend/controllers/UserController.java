package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.user.AddUserDto;
import com.mobileapp.backend.dtos.user.ChangePasswordDto;
import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.UserRepository;
import com.mobileapp.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
    public CommonResponseDto<UserDto> getUserById(@PathVariable("id") Long id) {
        UserEntity user = userService.getUserById(id);
        return new CommonResponseDto<>(new UserDto(user));
    }

    @GetMapping("/me")
    public CommonResponseDto<UserDto> getCurrentUser() {
        UserEntity user = userService.getCurrentUser();
        return new CommonResponseDto<>(new UserDto(user));
    }

    @GetMapping("")
    public PaginatedDataDto<UserDto> getAllUsers(@RequestParam(name = "page") int page) {
        return userService.getAllUsers(page);
    }


    @PostMapping("")
    public CommonResponseDto<UserDto> createUser(@RequestBody AddUserDto addUserDto) {
        if (!Objects.equals(addUserDto.getConfirmPassword(), addUserDto.getPassword())) {
            throw new CommonException(ResponseCode.ERROR, "Mật khẩu nhắc lại không đúng!");
        }

        UserEntity user = userService.createUser(addUserDto);
        return new CommonResponseDto<>(new UserDto(user));
    }

    @PutMapping("/{id}")
    public CommonResponseDto<String> editUser(@PathVariable("id") Long id,
                                              @RequestParam(value = "avatar", required = false) MultipartFile file,
                                              @RequestParam("username") String username,
                                              @RequestParam("email") String email,
                                              @RequestParam("avatarUrl") String avatarUrl,
                                              @RequestParam("phone") String phone) throws IOException {
        return new CommonResponseDto<>(userService.editUser(id, email, username, phone, avatarUrl, file));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deleteUser(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(userService.deleteUser(id));
    }

    @PutMapping("/change-password/{id}")
    public CommonResponseDto<String> changePassword(@PathVariable("id") Long id, @RequestBody ChangePasswordDto changePasswordDto) {
        userService.changePassword(id, changePasswordDto);
        return new CommonResponseDto<>("Change password successfully");
    }
}
