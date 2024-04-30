package com.mobileapp.backend.dtos.news;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddNewsDto {
    private String title;
    private String body;
    private MultipartFile image;
}
