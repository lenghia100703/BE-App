package com.mobileapp.backend.dtos.location;

import com.mobileapp.backend.entities.LocationEntity;
import lombok.Data;

import java.util.Date;

@Data
public class LocationDto {
    private Long id;
    private String name;
    private String description;
    private double longitude;
    private double latitude;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public LocationDto(LocationEntity location) {
        this.id = location.getId();
        this.name = location.getName();
        this.description = location.getDescription();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.createdBy = location.getCreatedBy();
        this.createdAt = location.getCreatedAt();
        this.updatedAt = location.getUpdatedAt();
        this.updatedBy = location.getUpdatedBy();

    }
}
