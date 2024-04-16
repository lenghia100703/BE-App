package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.UserEntity;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllUser(Pageable pageable);
    Optional<UserEntity> findUserByEmail(String email);
    Boolean existsByUsername(String username);
}
