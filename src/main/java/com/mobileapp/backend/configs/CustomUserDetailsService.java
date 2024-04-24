package com.mobileapp.backend.configs;

import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.UserRepository;
import com.mobileapp.backend.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mobileapp.backend.enums.ResponseCode.NOT_FOUND;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrentUserUtil currentUserUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) throw new CommonException(NOT_FOUND, "Email hoặc mật khẩu không đúng!");
        else return userOptional.get();
    }
}
