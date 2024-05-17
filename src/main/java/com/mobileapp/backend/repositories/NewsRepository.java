package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    Page<NewsEntity> findByTitleContaining(String title, Pageable pageable);
}
