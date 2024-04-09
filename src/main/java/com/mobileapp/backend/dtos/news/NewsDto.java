package com.mobileapp.backend.dtos.news;

import com.mobileapp.backend.dtos.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class NewsDto extends BaseDto {
    private String title;
    private String body;
    private String image;
}
