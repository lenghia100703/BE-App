package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.exhibition.ExhibitionItemDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.entities.ExhibitionItemEntity;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.repositories.ExhibitionItemRepository;
import com.mobileapp.backend.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SearchService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ExhibitionItemRepository exhibitionItemRepository;

    public PaginatedDataDto<NewsDto> searchNews(int page, String q) {
        List<NewsEntity> allNews = newsRepository.findAll();
        if (page >= 1 && !Objects.equals(q, "")) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<NewsEntity> newsPage = newsRepository.findByTitleContaining(q, pageable);

            List<NewsEntity> news = newsPage.getContent();
            return new PaginatedDataDto<>(news.stream().map(NewsDto::new).toList(), page, allNews.toArray().length);

        } else if (page < 1 || Objects.equals(q, "")) {
            return new PaginatedDataDto<>(allNews.stream().map(NewsDto::new).toList(), 1, allNews.toArray().length);
        }
        return null;
    }

    public PaginatedDataDto<ExhibitionItemDto> searchExhibitionItem(int page, String q) {
        List<ExhibitionItemEntity> allExhibitionItem = exhibitionItemRepository.findAll();
        if (page >= 1 && !Objects.equals(q, "")) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<ExhibitionItemEntity> exhibitionPage = exhibitionItemRepository.findByNameContaining(q, pageable);

            List<ExhibitionItemEntity> exhibition = exhibitionPage.getContent();
            return new PaginatedDataDto<>(exhibition.stream().map(ExhibitionItemDto::new).toList(), page, allExhibitionItem.toArray().length);

        } else if (page < 1 || Objects.equals(q, "")) {
            return new PaginatedDataDto<>(allExhibitionItem.stream().map(ExhibitionItemDto::new).toList(), 1, allExhibitionItem.toArray().length);
        }
        return null;
    }
}
