package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.post.PostDto;
import com.mobileapp.backend.entities.PostEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.PostRepository;
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
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    GithubUtil githubUtil;

    public PaginatedDataDto<PostDto> getAllPosts(int page) {
        List<PostEntity> allPosts = postRepository.findAll();
        if (page >= 1) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<PostEntity> postPage = postRepository.findAll(pageable);

            List<PostEntity> post = postPage.getContent();

            return new PaginatedDataDto<>(post.stream().map(PostDto::new).toList(), page, allPosts.toArray().length);
        } else {
            return new PaginatedDataDto<>(allPosts.stream().map(PostDto::new).toList(), 1, allPosts.toArray().length);
        }

    }

    public PostEntity getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND, "News not found!"));
    }

    public PostDto createPost(String title, String description, String imageUrl, MultipartFile file) throws IOException {
        PostEntity post = new PostEntity();
        post.setTitle(title);
        post.setCreatedAt(new Date(System.currentTimeMillis()));
        post.setCreatedBy(userService.getCurrentUser().getEmail());
        post.setDescription(description);
        post.setRating(title.length() % 4);

        if (file != null) {
            post.setImage(githubUtil.uploadImage(file, "post"));
        } else {
            post.setImage(imageUrl);
        }

        return new PostDto(postRepository.save(post));
    }

    public PostDto createPostById(Long id, String title, String description, String imageUrl, MultipartFile file) throws IOException {
        PostEntity post = new PostEntity();
        post.setTitle(title);
        post.setCreatedAt(new Date(System.currentTimeMillis()));
        post.setCreatedBy(userService.getUserById(id).getEmail());
        post.setDescription(description);
        post.setRating(title.length() % 4);

        if (file != null) {
            post.setImage(githubUtil.uploadImage(file, "post"));
        } else {
            post.setImage(imageUrl);
        }

        return new PostDto(postRepository.save(post));
    }

    public String deletePost(Long id) {
        PostEntity post = postRepository.getById(id);

        if (post == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        postRepository.delete(post);
        return "Deleted successfully";
    }

    public String editPost(Long id, String title, String imageUrl, MultipartFile file) throws IOException {
        PostEntity post = postRepository.getById(id);
        if (post == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }
        post.setTitle(title);
        post.setUpdatedBy(userService.getCurrentUser().getEmail());
        post.setUpdatedAt(new Date(System.currentTimeMillis()));

        if (file != null) {
            post.setImage(githubUtil.uploadImage(file, "post"));
        } else {
            post.setImage(imageUrl);
        }
        postRepository.save(post);

        return "Edited successfully";
    }

}
