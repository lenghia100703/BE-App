package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.repositories.NewsRepository;
import com.mobileapp.backend.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "", consumes = {"multipart/form-data"})
    public CommonResponseDto<NewsDto> createNews(@RequestParam(value = "image", required = false) MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("imageUrl") String imageUrl,
                                                 @RequestParam("body") String body) throws IOException {

        return new CommonResponseDto<>(newsService.createNews(title, body, imageUrl, file));
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
                                              @RequestParam("imageUrl") String imageUrl,
                                              @RequestParam("body") String body) throws IOException {

        return new CommonResponseDto<>(newsService.editNews(id, title, body, imageUrl, file));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deleteNews(@PathVariable Long id) {
        return new CommonResponseDto<>(newsService.deleteNews(id));
    }

}
