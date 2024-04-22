package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.user.AddUserDto;
import com.mobileapp.backend.dtos.user.ChangePasswordDto;
import com.mobileapp.backend.dtos.user.EditUserDto;
import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

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
    public PaginatedDataDto<UserDto> getAllUser(Pageable pageable, @RequestParam(name = "page") int page) {
        return userService.getAllUser(pageable, page);
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
    public CommonResponseDto<UserDto> editUser(@PathVariable("id") Long id, @RequestBody EditUserDto editUserDto) {
        UserEntity userById = userService.getUserById(id);
        if (userById == null) {
            throw new CommonException(ResponseCode.NOT_FOUND, "Không tìm thấy người dùng!");
        }

        UserEntity editedUser = userService.editUser(userById, editUserDto);
        return new CommonResponseDto<>(new UserDto(editedUser));
    }

    @PutMapping("/change-password/{id}")
    public CommonResponseDto<String> changePassword(@PathVariable("id") Long id, @RequestBody ChangePasswordDto changePasswordDto) {
        UserEntity userById = userService.getUserById(id);

        if (userById == null) {
            throw new CommonException(ResponseCode.NOT_FOUND, "Không tìm thấy người dùng!");
        }

        userService.changePassword(userById, changePasswordDto);
        return new CommonResponseDto<>(ResponseCode.SUCCESS);
    }
}
