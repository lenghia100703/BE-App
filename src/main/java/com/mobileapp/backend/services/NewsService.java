package com.mobileapp.backend.services;

import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    public String getListNews() {
        return "List: <NewsDto>";
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
