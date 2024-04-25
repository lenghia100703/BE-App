package com.mobileapp.backend.dtos.news;

import lombok.Data;

@Data
public class AddNewsDto {
    private String title;
    private String body;
    private String image;
}
