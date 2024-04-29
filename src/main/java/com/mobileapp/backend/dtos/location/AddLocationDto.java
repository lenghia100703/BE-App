package com.mobileapp.backend.dtos.location;

import lombok.Data;

@Data
public class AddLocationDto {
    private String name;
    private String description;
    private double longitude;
    private double latitude;
}
