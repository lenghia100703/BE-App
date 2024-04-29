package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.exhibition.ExhibitionItemDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.repositories.ExhibitionItemRepository;
import com.mobileapp.backend.services.ExhibitionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExhibitionItemController {
    @Autowired
    ExhibitionItemService exhibitionItemService;

    @Autowired
    ExhibitionItemRepository exhibitionItemRepository;

    @GetMapping("")
    public List<ExhibitionItemDto> getAll() {
        return exhibitionItemRepository.findAll().stream().map(ExhibitionItemDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CommonResponseDto<ExhibitionItemDto> getExhibitionItemById(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(new ExhibitionItemDto(exhibitionItemService.getExhibitionItemById(id)));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deleteExhibitionItem(@PathVariable Long id) {
        return new CommonResponseDto<>(exhibitionItemService.deleteExhibitionItem(id));
    }

    @RequestMapping(value = "", consumes = { "multipart/form-data" })
    public CommonResponseDto<ExhibitionItemDto> createNews(@RequestParam(value = "image", required = false) MultipartFile file,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("imageUrl") String imageUrl,
                                                 @RequestParam("description") String description) throws IOException {

        return new CommonResponseDto<>(exhibitionItemService.createExhibitionItem(name, description, file, imageUrl));
    }

    @PutMapping("/{id}")
    public CommonResponseDto<String> editExhibitionItem(@PathVariable Long id,
                                              @RequestParam(value = "image", required = false) MultipartFile file,
                                              @RequestParam("name") String name,
                                              @RequestParam("imageUrl") String imageUrl,
                                              @RequestParam("description") String description) throws IOException {

        return new CommonResponseDto<>(exhibitionItemService.editExhibitionItem(id, name, description, file, imageUrl));
    }
}
