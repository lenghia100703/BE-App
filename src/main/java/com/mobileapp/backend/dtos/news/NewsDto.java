package com.mobileapp.backend.dtos.news;

import com.mobileapp.backend.dtos.BaseDto;
import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.entities.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Setter
@Getter
public class NewsDto {
    private Long id;
    private String title;
    private String body;
    private String image;
    private UserDto adminId;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public NewsDto(NewsEntity news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.body = news.getBody();
        this.image = news.getImage();
        this.adminId = new UserDto(news.getAdminId());
        this.createdBy = news.getCreatedBy();
        this.createdAt = news.getCreatedAt();
        this.updatedAt = news.getUpdatedAt();
        this.updatedBy = news.getUpdatedBy();
    }
}
