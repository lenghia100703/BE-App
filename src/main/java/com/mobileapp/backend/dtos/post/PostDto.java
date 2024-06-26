package com.mobileapp.backend.dtos.post;

import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.PostEntity;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String image;
    private String description;
    private int rating;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public PostDto(PostEntity post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.image = post.getImage();
        this.description = post.getDescription();
        this.rating = post.getRating();
        this.createdBy = post.getCreatedBy();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.updatedBy = post.getUpdatedBy();
    }
}
