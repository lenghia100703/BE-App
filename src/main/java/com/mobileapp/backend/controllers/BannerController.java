package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.banner.BannerDto;
import com.mobileapp.backend.services.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping(value = "", consumes = {"multipart/form-data"})
    public CommonResponseDto<BannerDto> createBanner(@RequestParam(value = "image", required = false) MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("imageUrl") String imageUrl) throws IOException {

        return new CommonResponseDto<>(bannerService.createBanner(title, imageUrl, file));
    }


    @GetMapping("")
    public PaginatedDataDto<BannerDto> getAllBanner(@RequestParam(name = "page") int page) {
        return bannerService.getAllBanner(page);
    }

    @GetMapping("/active")
    public List<BannerDto> getALlBannerIsActive() {
        return bannerService.getALlBannerIsActive();
    }


    @GetMapping("/{id}")
    public CommonResponseDto<BannerDto> getBannerById(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(new BannerDto(bannerService.getBannerById(id)));
    }

    @PutMapping("/{id}")
    public CommonResponseDto<String> editBanner(@PathVariable Long id,
                                              @RequestParam(value = "image", required = false) MultipartFile file,
                                              @RequestParam("title") String title,
                                              @RequestParam("imageUrl") String imageUrl,
                                              @RequestParam("body") Boolean active) throws IOException {

        return new CommonResponseDto<>(bannerService.editBanner(id, title, active, imageUrl, file));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deleteBanner(@PathVariable Long id) {
        return new CommonResponseDto<>(bannerService.deleteBanner(id));
    }
}
