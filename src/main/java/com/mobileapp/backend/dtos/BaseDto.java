package com.mobileapp.backend.dtos;

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
}
