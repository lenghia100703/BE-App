package com.mobileapp.backend.dtos;

import com.mobileapp.backend.entities.BaseEntity;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto {
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public BaseDto(BaseEntity news) {
        this.createdBy = news.getCreatedBy();
        this.createdAt = news.getCreatedAt();
        this.updatedAt = news.getUpdatedAt();
        this.updatedBy = news.getUpdatedBy();
    }
}
