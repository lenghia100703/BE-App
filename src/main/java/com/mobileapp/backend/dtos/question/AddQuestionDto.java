package com.mobileapp.backend.dtos.question;

import lombok.Data;

@Data
public class AddQuestionDto {
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correctAnswer;
}
