package com.mobileapp.backend.dtos.answer;

import com.mobileapp.backend.entities.AnswerEntity;
import lombok.Data;

import java.util.Date;

@Data
public class AnswerDto {
    private Long id;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public AnswerDto(AnswerEntity answerEntity) {
        this.id = answerEntity.getId();
        this.answer1 = answerEntity.getAnswer1();
        this.answer2 = answerEntity.getAnswer2();
        this.answer3 = answerEntity.getAnswer3();
        this.answer4 = answerEntity.getAnswer4();
        this.createdBy = answerEntity.getCreatedBy();
        this.createdAt = answerEntity.getCreatedAt();
        this.updatedAt = answerEntity.getUpdatedAt();
        this.updatedBy = answerEntity.getUpdatedBy();
    }
}
