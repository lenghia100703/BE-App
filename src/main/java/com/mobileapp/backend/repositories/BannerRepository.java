package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
    @Query("SELECT b FROM BannerEntity b WHERE b.isActive = true")
    List<BannerEntity> findAllBannerIsActive();
}
