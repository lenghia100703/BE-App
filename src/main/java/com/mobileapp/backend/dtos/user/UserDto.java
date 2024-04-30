package com.mobileapp.backend.dtos.user;

import com.mobileapp.backend.entities.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String role;
    private String avatar;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public UserDto(UserEntity newUser) {
        this.id = newUser.getId();
        this.username = newUser.getUsername();
        this.email = newUser.getEmail();
        this.phone = newUser.getPhone();
        this.role = newUser.getRole();
        this.avatar = newUser.getAvatar();
        this.createdBy = newUser.getCreatedBy();
        this.createdAt = newUser.getCreatedAt();
        this.updatedAt = newUser.getUpdatedAt();
        this.updatedBy = newUser.getUpdatedBy();
    }
}
