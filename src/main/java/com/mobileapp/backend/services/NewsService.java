package com.mobileapp.backend.services;

import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    public PaginatedDataDto<NewsDto> getAllNews(Pageable pageable, int page) {
        Page<NewsEntity> newsPage = newsRepository.findAll(pageable);

        List<NewsEntity> news = newsPage.getContent();

        return new PaginatedDataDto<>(news.stream().map(NewsDto::new).toList(), page, newsPage.getTotalPages());
    }

    public NewsEntity getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND, "News not found!"));
    }

    public String createNews() {
        return "Created successfully";
    }

    public String editNews() {
        return "Edited successfully";
    }

    public String deleteNews() {
        return "Deleted successfully";
    }
}
