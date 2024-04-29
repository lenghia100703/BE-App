package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.news.NewsDto;
import com.mobileapp.backend.dtos.exhibition.ExhibitionItemDto;
import com.mobileapp.backend.entities.ExhibitionItemEntity;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.ExhibitionItemRepository;
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
public class ExhibitionItemService {
    @Autowired
    ExhibitionItemRepository exhibitionItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    GithubUtil githubUtil;

    public PaginatedDataDto<ExhibitionItemDto> getAllExhibitions(int page) {
        Pageable pageable = PageRequest.of(page, PageableConstants.LIMIT);
        Page<ExhibitionItemEntity> exhibitionPage = exhibitionItemRepository.findAll(pageable);

        List<ExhibitionItemEntity> exhibition = exhibitionPage.getContent();

        return new PaginatedDataDto<>(exhibition.stream().map(ExhibitionItemDto::new).toList(), page, exhibitionPage.getTotalPages());
    }

    public ExhibitionItemEntity getExhibitionItemById(Long id) {
        return exhibitionItemRepository.findById(id).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND, "Exhibition Item not found!"));
    }

    public ExhibitionItemDto createExhibitionItem(String name, String description, MultipartFile file, String imageUrl) throws IOException {
        ExhibitionItemEntity exhibition = new ExhibitionItemEntity();
        exhibition.setName(name);
        exhibition.setDescription(description);
        if (file == null) {
            exhibition.setImage(imageUrl);
        } else {
            exhibition.setImage(githubUtil.uploadImage(file, "exhibition"));
        }
        exhibition.setAdminId(userService.getCurrentUser());
        exhibition.setCreatedBy(userService.getCurrentUser().getEmail());
        exhibition.setCreatedAt(new Date(System.currentTimeMillis()));
        return new ExhibitionItemDto(exhibitionItemRepository.save(exhibition));
    }

    public String editExhibitionItem(Long id, String name, String description, MultipartFile file, String imageUrl) throws IOException {
        ExhibitionItemEntity exhibition = exhibitionItemRepository.getById(id);
        if (exhibition == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        exhibition.setName(name);
        exhibition.setDescription(description);
        exhibition.setUpdatedBy(userService.getCurrentUser().getEmail());
        exhibition.setUpdatedAt(new Date(System.currentTimeMillis()));

        if (file == null) {
            exhibition.setImage(imageUrl);
        } else {
            exhibition.setImage(githubUtil.uploadImage(file, "exhibition"));
        }

        exhibitionItemRepository.save(exhibition);
        return "Edited successfully";
    }

    public String deleteExhibitionItem(Long id) {
        ExhibitionItemEntity exhibition = exhibitionItemRepository.getById(id);
        if (exhibition == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }
        exhibitionItemRepository.delete(exhibition);
        return "Deleted successfully";
    }
}
