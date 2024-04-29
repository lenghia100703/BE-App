package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.news.AddNewsDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.NewsRepository;
import com.mobileapp.backend.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    UserService userService;

    @Autowired
    GithubUtil githubUtil;

    public PaginatedDataDto<NewsDto> getAllNews(int page) {
        List<NewsEntity> allNews = newsRepository.findAll();
        if (page >= 1) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<NewsEntity> newsPage = newsRepository.findAll(pageable);

            List<NewsEntity> news = newsPage.getContent();
            return new PaginatedDataDto<>(news.stream().map(NewsDto::new).toList(), page, allNews.toArray().length);

        } else {
            return new PaginatedDataDto<>(allNews.stream().map(NewsDto::new).toList(), 1, allNews.toArray().length);
        }
    }

    public NewsEntity getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND, "News not found!"));
    }

    public NewsDto createNews(String title, String body, String imageUrl, MultipartFile file) throws IOException {
        NewsEntity news = new NewsEntity();
        news.setTitle(title);
        news.setBody(body);
        if (file != null) {
            news.setImage(githubUtil.uploadImage(file, "news"));
        } else {
            news.setImage(imageUrl);
        }
        news.setAdminId(userService.getCurrentUser());
        news.setCreatedBy(userService.getCurrentUser().getEmail());
        news.setCreatedAt(new Date(System.currentTimeMillis()));
        return new NewsDto(newsRepository.save(news));
    }

    public String editNews(Long id, String title, String body, String imageUrl, MultipartFile file) throws IOException {
        NewsEntity news = newsRepository.getById(id);
        if (news == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        news.setTitle(title);
        news.setBody(body);
        news.setUpdatedBy(userService.getCurrentUser().getEmail());
        news.setUpdatedAt(new Date(System.currentTimeMillis()));

        if (file != null) {
            news.setImage(githubUtil.uploadImage(file, "news"));
        } else {
            news.setImage(imageUrl);
        }

        newsRepository.save(news);
        return "Edited successfully";
    }

    public String deleteNews(Long id) {
        NewsEntity news = newsRepository.getById(id);
        if (news == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }
        newsRepository.delete(news);
        return "Deleted successfully";
    }
}
