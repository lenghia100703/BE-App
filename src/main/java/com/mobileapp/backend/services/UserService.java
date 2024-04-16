package com.mobileapp.backend.services;

import com.mobileapp.backend.dtos.user.AddUserDto;
import com.mobileapp.backend.dtos.user.ChangePasswordDto;
import com.mobileapp.backend.dtos.user.EditUserDto;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mobileapp.backend.enums.ResponseCode.ERROR;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public UserEntity getCurrentUser(Long id) {
        return userRepository.getReferenceById(id);
    }

    public UserEntity createUser(AddUserDto addUserDto) {
        if (findByEmail(addUserDto.getEmail()) != null) {
            throw new CommonException(ERROR, "Email đã tồn tại");
        }

        UserEntity user = new UserEntity();
        user.setEmail(addUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(addUserDto.getPassword()));
        user.setPhone(addUserDto.getPhone());
        user.setUsername(addUserDto.getUsername());
        user.setRole("USER");

        return userRepository.save(user);
    }

    public UserEntity editUser(UserEntity userEntity, EditUserDto editUserDto) {
        userEntity.setUsername(editUserDto.getUsername());
        userEntity.setPhone(editUserDto.getPhone());
        return userRepository.save(userEntity);
    }

    public void changePassword(UserEntity userEntity, ChangePasswordDto changePasswordDto) {
        if (!changePasswordDto.getPassword().equals("")) {
            if (changePasswordDto.getPassword().length() < 8) {
                throw new CommonException(ERROR, "Mật khẩu phải có ít nhất 8 ký tự");
            }
            userEntity.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        }
        userRepository.save(userEntity);
    }

    public UserEntity findByEmail(String email) {
        Optional<UserEntity> userOptional = userRepository.findUserByEmail(email);
        return userOptional.orElse(null);
    }
}
