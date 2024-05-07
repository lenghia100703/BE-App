package com.mobileapp.backend.dtos.banner;

import com.mobileapp.backend.entities.BannerEntity;
import lombok.Data;

import java.util.Date;

@Data
public class BannerDto {
    private Long id;
    private String title;
    private String image;
    private boolean isActive;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public BannerDto(BannerEntity banner) {
        this.id = banner.getId();
        this.title = banner.getTitle();
        this.image = banner.getImage();
        this.isActive = banner.isActive();
        this.createdBy = banner.getCreatedBy();
        this.createdAt = banner.getCreatedAt();
        this.updatedAt = banner.getUpdatedAt();
        this.updatedBy = banner.getUpdatedBy();
    }
}
