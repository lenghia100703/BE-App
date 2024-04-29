package com.mobileapp.backend.services;

import com.mobileapp.backend.dtos.question.AddQuestionDto;
import com.mobileapp.backend.dtos.question.EditQuestionDto;
import com.mobileapp.backend.dtos.question.QuestionDto;
import com.mobileapp.backend.entities.AnswerEntity;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.entities.QuestionEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.AnswerRepository;
import com.mobileapp.backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserService userService;

    public QuestionEntity getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND, "Question not found!"));
    }

    public QuestionDto createQuestion(AddQuestionDto addQuestionDto) {
        QuestionEntity question = new QuestionEntity();
        AnswerEntity answer = new AnswerEntity();
        question.setQuestion(addQuestionDto.getQuestion());
        question.setCorrectAnswer(addQuestionDto.getCorrectAnswer());
        question.setAdminId(userService.getCurrentUser());
        question.setCreatedBy(userService.getCurrentUser().getEmail());
        question.setCreatedAt(new Date(System.currentTimeMillis()));
        answer.setAnswer1(addQuestionDto.getAnswer1());
        answer.setAnswer2(addQuestionDto.getAnswer2());
        answer.setAnswer3(addQuestionDto.getAnswer3());
        answer.setAnswer4(addQuestionDto.getAnswer4());
        answerRepository.save(answer);
        question.setAnswerId(answer);

        return new QuestionDto(questionRepository.save(question));
    }

    public String deleteQuestion(Long id) {
        QuestionEntity question = questionRepository.getById(id);
        if (question == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }
        questionRepository.delete(question);

        return "Deleted successfully";
    }

    public String editQuestion(Long id, EditQuestionDto editQuestionDto) {
        QuestionEntity question = questionRepository.getById(id);
        if (question == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }
        AnswerEntity answer = answerRepository.getById(question.getAnswerId().getId());
        if (answer == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        question.setQuestion(editQuestionDto.getQuestion());
        question.setCorrectAnswer(editQuestionDto.getCorrectAnswer());
        question.setUpdatedBy(userService.getCurrentUser().getEmail());
        question.setUpdatedAt(new Date(System.currentTimeMillis()));
        answer.setAnswer1(editQuestionDto.getAnswer1());
        answer.setAnswer2(editQuestionDto.getAnswer2());
        answer.setAnswer3(editQuestionDto.getAnswer3());
        answer.setAnswer4(editQuestionDto.getAnswer4());
        answerRepository.save(answer);
        question.setAnswerId(answer);
        questionRepository.save(question);

        return "Edited successfully";
    }
}
