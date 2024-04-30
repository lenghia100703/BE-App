package com.mobileapp.backend.dtos.exhibition;

import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.ExhibitionItemEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ExhibitionItemDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private UserDto adminId;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public ExhibitionItemDto(ExhibitionItemEntity exhibitionItem) {
        this.id = exhibitionItem.getId();
        this.name = exhibitionItem.getName();
        this.description = exhibitionItem.getDescription();
        this.image = exhibitionItem.getImage();
        this.adminId = new UserDto(exhibitionItem.getAdminId());
        this.createdBy = exhibitionItem.getCreatedBy();
        this.createdAt = exhibitionItem.getCreatedAt();
        this.updatedAt = exhibitionItem.getUpdatedAt();
        this.updatedBy = exhibitionItem.getUpdatedBy();
    }
}
