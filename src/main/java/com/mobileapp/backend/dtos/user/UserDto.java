package com.mobileapp.backend.dtos.user;

import com.mobileapp.backend.dtos.BaseDto;
import com.mobileapp.backend.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String role;
    private String avatar;

    public UserDto(UserEntity newUser) {
        this.id = newUser.getId();
        this.username = newUser.getUsername();
        this.email = newUser.getEmail();
        this.phone = newUser.getPhone();
        this.role = newUser.getRole();
        this.avatar = newUser.getAvatar();
    }
}
