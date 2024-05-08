package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.banner.BannerDto;
import com.mobileapp.backend.entities.BannerEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.BannerRepository;
import com.mobileapp.backend.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class BannerService {
    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    UserService userService;

    @Autowired
    GithubUtil githubUtil;

    public List<BannerDto> getALlBannerIsActive() {
        return bannerRepository.findAllBannerIsActive().stream().map(BannerDto::new).toList();
    }

    public PaginatedDataDto<BannerDto> getAllBanner(int page) {
        List<BannerEntity> allBanner = bannerRepository.findAll();
        if (page >= 1) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<BannerEntity> bannerPage = bannerRepository.findAll(pageable);

            List<BannerEntity> banner = bannerPage.getContent();
            return new PaginatedDataDto<>(banner.stream().map(BannerDto::new).toList(), page, allBanner.toArray().length);

        } else {
            return new PaginatedDataDto<>(allBanner.stream().map(BannerDto::new).toList(), 1, allBanner.toArray().length);
        }
    }

    public BannerEntity getBannerById(Long id) {
        return bannerRepository.findById(id).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND, "Banner not found!"));
    }

    public BannerDto createBanner(String title, String active, String imageUrl, MultipartFile file) throws IOException {
        BannerEntity banner = new BannerEntity();
        banner.setTitle(title);
        banner.setIsActive(active);
        if (file != null) {
            banner.setImage(githubUtil.uploadImage(file, "banner"));
        } else {
            banner.setImage(imageUrl);
        }

        banner.setCreatedBy(userService.getCurrentUser().getEmail());
        banner.setCreatedAt(new Date(System.currentTimeMillis()));
        return new BannerDto(bannerRepository.save(banner));
    }

    public String editBanner(Long id, String title, String active, String imageUrl, MultipartFile file) throws IOException {
        BannerEntity banner = bannerRepository.getById(id);
        if (banner == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        banner.setTitle(title);
        banner.setIsActive(active);
        banner.setUpdatedBy(userService.getCurrentUser().getEmail());
        banner.setUpdatedAt(new Date(System.currentTimeMillis()));

        if (file != null) {
            banner.setImage(githubUtil.uploadImage(file, "banner"));
        } else {
            banner.setImage(imageUrl);
        }

        bannerRepository.save(banner);
        return "Edited successfully";
    }

    public String deleteBanner(Long id) {
        BannerEntity banner = bannerRepository.getById(id);
        if (banner == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }
        bannerRepository.delete(banner);
        return "Deleted successfully";
    }
}
