package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.question.AddQuestionDto;
import com.mobileapp.backend.dtos.question.EditQuestionDto;
import com.mobileapp.backend.dtos.question.QuestionDto;
import com.mobileapp.backend.repositories.QuestionRepository;
import com.mobileapp.backend.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("")
    public List<QuestionDto> getAll() {
        return questionRepository.findAll().stream().map(QuestionDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CommonResponseDto<QuestionDto> getQuestionById(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(new QuestionDto(questionService.getQuestionById(id)));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deleteQuestion(@PathVariable Long id) {
        return new CommonResponseDto<>(questionService.deleteQuestion(id));
    }

    @PostMapping(value = "")
    public CommonResponseDto<QuestionDto> createQuestion(@RequestBody AddQuestionDto addQuestionDto)  {
        return new CommonResponseDto<>(questionService.createQuestion(addQuestionDto));
    }

    @PutMapping("/{id}")
    public CommonResponseDto<String> editQuestion(@PathVariable("id") Long id, @RequestBody EditQuestionDto editQuestionDto) {
        return new CommonResponseDto<>(questionService.editQuestion(id, editQuestionDto));
    }
}
