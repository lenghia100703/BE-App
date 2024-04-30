package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.user.AddUserDto;
import com.mobileapp.backend.dtos.user.ChangePasswordDto;
import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.UserRepository;
import com.mobileapp.backend.utils.GithubUtil;
import com.mobileapp.backend.utils.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mobileapp.backend.enums.ResponseCode.ERROR;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GithubUtil githubUtil;

    @Value("${default.avatar}")
    String defaultAvatar;

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getCurrentUser() {
        Long id = getCurrentUserId();
        return getUserById(id);
    }

    public PaginatedDataDto<UserDto> getAllUsers(int page) {
        Stream<UserEntity> allUsers = userRepository.findAll().stream().filter(user -> user.getRole().equals("USER"));
        if (page >= 1) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<UserEntity> userPage = userRepository.findAll(pageable);

            List<UserDto> userDtos = userPage.getContent().stream()
                    .filter(user -> user.getRole().equals("USER"))
                    .map(UserDto::new)
                    .collect(Collectors.toList());

            return new PaginatedDataDto<>(userDtos, page, allUsers.toArray().length);
        } else {
            List<UserDto> userDtos = userRepository.findAll().stream()
                    .filter(user -> user.getRole().equals("USER"))
                    .map(UserDto::new)
                    .collect(Collectors.toList());
            return new PaginatedDataDto<>(userDtos, 1, allUsers.toArray().length);
        }

    }

    public UserEntity createUser(AddUserDto addUserDto) {
        if (findByEmail(addUserDto.getEmail()) != null) {
            throw new CommonException(ERROR, "Email đã tồn tại");
        }

        UserEntity user = new UserEntity();
        user.setEmail(addUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(addUserDto.getPassword()));
        user.setUsername(addUserDto.getUsername());
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setCreatedBy(getCurrentUser().getEmail());
        user.setAvatar(defaultAvatar);
        user.setRole("USER");

        return userRepository.save(user);
    }

    public String editUser(Long id, String email, String username, String phone, String avatarUrl, MultipartFile file) throws IOException {
        UserEntity user = userRepository.getById(id);
        if (user == null) {
            throw new CommonException(ResponseCode.NOT_FOUND, "Không tìm thấy người dùng!");
        }

        user.setEmail(email);
        user.setUsername(username);
        user.setUpdatedBy(getCurrentUser().getEmail());
        user.setUpdatedAt(new Date(System.currentTimeMillis()));

        if (!Objects.equals(phone, "")) {
            user.setPhone(phone);
        } else {
            user.setPhone("");
        }

        if (file != null) {
            user.setAvatar(githubUtil.uploadImage(file, "avatar"));
        } else {
            user.setAvatar(avatarUrl);
        }

        userRepository.save(user);
        return "Edited successfully";
    }

    public String deleteUser(Long id) {
        UserEntity user = userRepository.getById(id);

        if (user == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        userRepository.delete(user);
        return "Deleted successfully";
    }

    public void changePassword(Long id, ChangePasswordDto changePasswordDto) {
        UserEntity userEntity = userRepository.getById(id);
        if (userEntity == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        if (!changePasswordDto.getPassword().equals("") && passwordEncoder.matches(changePasswordDto.getCurrentPassword(), userEntity.getPassword())) {
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
