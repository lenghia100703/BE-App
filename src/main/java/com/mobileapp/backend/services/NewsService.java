package com.mobileapp.backend.services;

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
