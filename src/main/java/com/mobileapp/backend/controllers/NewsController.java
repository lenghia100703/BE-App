package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.services.NewsService;
import com.mobileapp.backend.services.StorageService;
import com.mobileapp.backend.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    StorageService storageService;

    @Autowired
    NewsService newsService;

    @PutMapping("")
    public CommonResponseDto<NewsDto> createNews(@RequestParam("image") MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("body") String body) throws IOException {

        return new CommonResponseDto<>(newsService.createNews(title, body, file));
    }


    public ResponseEntity<?> getImage(Long id) {
        NewsEntity news = newsService.getNewsById(id);
        byte[] imageData = storageService.getImage(news);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "image/png")
                .body(imageData);
    }


}
