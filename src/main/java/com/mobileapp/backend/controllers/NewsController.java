package com.mobileapp.backend.controllers;

import com.auth0.jwt.interfaces.Payload;
import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.repositories.NewsRepository;
import com.mobileapp.backend.services.NewsService;
import com.mobileapp.backend.services.StorageService;
import com.mobileapp.backend.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    StorageService storageService;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "", consumes = { "multipart/form-data" })
    public CommonResponseDto<NewsDto> createNews(@RequestParam(value = "image", required = false) MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("body") String body) throws IOException {

        return new CommonResponseDto<>(newsService.createNews(title, body, file));
    }


    @GetMapping("")
    public PaginatedDataDto<NewsDto> getAllNews(@RequestParam(name = "page") int page) {
        return newsService.getAllNews(page);
    }


    @GetMapping("/{id}")
    public CommonResponseDto<NewsDto> getNewsById(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(new NewsDto(newsService.getNewsById(id)));
    }

    @PutMapping("/{id}")
    public CommonResponseDto<String> editNews(@PathVariable Long id,
                           @RequestParam(value = "image", required = false) MultipartFile file,
                           @RequestParam("title") String title,
                           @RequestParam("body") String body) throws IOException {

        return new CommonResponseDto<>(newsService.editNews(id, title, body, file));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deleteNews(@PathVariable Long id) {
        return new CommonResponseDto<>(newsService.deleteNews(id));
    }

}
