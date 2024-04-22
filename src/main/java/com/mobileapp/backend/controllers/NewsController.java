package com.mobileapp.backend.controllers;

import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.services.NewsService;
import com.mobileapp.backend.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    StorageService storageService;

    @Autowired
    NewsService newsService;



    public ResponseEntity<?> getImage(Long id) {
        NewsEntity news = newsService.getNewsById(id);
        byte[] imageData = storageService.getImage(news);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "image/png")
                .body(imageData);
    }


}
