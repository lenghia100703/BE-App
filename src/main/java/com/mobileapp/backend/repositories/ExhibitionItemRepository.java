package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.ExhibitionItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionItemRepository extends JpaRepository<ExhibitionItemEntity, Long> {
    Page<ExhibitionItemEntity> findByNameContaining(String name, Pageable pageable);
}
