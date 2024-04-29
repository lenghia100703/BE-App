package com.mobileapp.backend.dtos.question;

import com.mobileapp.backend.dtos.answer.AnswerDto;
import com.mobileapp.backend.dtos.user.UserDto;
import lombok.Data;

import java.util.Date;

@Data
public class AddQuestionDto {
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correctAnswer;
}
