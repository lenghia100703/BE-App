package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
