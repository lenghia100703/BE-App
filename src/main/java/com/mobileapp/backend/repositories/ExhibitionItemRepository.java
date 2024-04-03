package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.ExhibitionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionItemRepository extends JpaRepository<ExhibitionItemEntity, Long> {
}
