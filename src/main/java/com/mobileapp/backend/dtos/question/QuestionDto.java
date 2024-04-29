package com.mobileapp.backend.dtos.question;

import com.mobileapp.backend.dtos.answer.AnswerDto;
import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.entities.QuestionEntity;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionDto {
    private Long id;
    private String question;
    private String correctAnswer;
    private UserDto adminId;
    private AnswerDto answerId;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public QuestionDto(QuestionEntity questionEntity) {
        this.id = questionEntity.getId();
        this.question = questionEntity.getQuestion();
        this.correctAnswer = questionEntity.getCorrectAnswer();
        this.adminId = new UserDto(questionEntity.getAdminId());
        this.answerId = new AnswerDto(questionEntity.getAnswerId());
        this.createdBy = questionEntity.getCreatedBy();
        this.createdAt = questionEntity.getCreatedAt();
        this.updatedAt = questionEntity.getUpdatedAt();
        this.updatedBy = questionEntity.getUpdatedBy();
    }
}
