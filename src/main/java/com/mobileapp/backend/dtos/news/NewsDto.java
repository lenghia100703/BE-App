package com.mobileapp.backend.dtos.news;

import com.mobileapp.backend.dtos.BaseDto;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.entities.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
public class NewsDto extends BaseDto {
    private Long id;
    private String title;
    private String body;
    private String image;
    private UserEntity adminId;

    public NewsDto(NewsEntity news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.body = news.getBody();
        this.image = news.getImage();
        this.adminId = news.getAdminId();
    }
}
