package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.user.AddUserDto;
import com.mobileapp.backend.dtos.user.ChangePasswordDto;
import com.mobileapp.backend.dtos.user.EditUserDto;
import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.UserRepository;
import com.mobileapp.backend.utils.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mobileapp.backend.enums.ResponseCode.ERROR;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getCurrentUser() {
        Long id = getCurrentUserId();
        return getUserById(id);
    }

    public PaginatedDataDto<UserDto> getAllUser(int page) {
        Pageable pageable = PageRequest.of(page, PageableConstants.LIMIT);
        Page<UserEntity> userPage = userRepository.findAll(pageable);

        List<UserDto> userDtos = userPage.getContent().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new PaginatedDataDto<>(userDtos, page, userPage.getTotalPages());
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

    public Long getCurrentUserId() {
        return SecurityContextUtil.getCurrentUserId();
    }
}
