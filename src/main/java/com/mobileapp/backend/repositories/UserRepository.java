package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);

    Boolean existsByUsername(String username);
}
