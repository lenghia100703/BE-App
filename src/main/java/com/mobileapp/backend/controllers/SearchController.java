package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.exhibition.ExhibitionItemDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/news")
    public PaginatedDataDto<NewsDto> searchNews(@RequestParam(name = "page") int page,
                                                @RequestParam(name = "q") String q) {

        return searchService.searchNews(page, q);
    }

    @GetMapping("/exhibition")
    public PaginatedDataDto<ExhibitionItemDto> searchExhibitionItem(@RequestParam(name = "page") int page,
                                                          @RequestParam(name = "q") String q) {

        return searchService.searchExhibitionItem(page, q);
    }
}
