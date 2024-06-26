package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.post.PostDto;
import com.mobileapp.backend.repositories.PostRepository;
import com.mobileapp.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @GetMapping("")
    public PaginatedDataDto<PostDto> getAllPosts(@RequestParam(name = "page") int page) {
        return postService.getAllPosts(page);
    }

    @RequestMapping(value = "", consumes = {"multipart/form-data"})
    public CommonResponseDto<PostDto> createPost(@RequestParam(value = "image", required = false) MultipartFile file,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("imageUrl") String imageUrl) throws IOException {

        return new CommonResponseDto<>(postService.createPost(title, description, imageUrl, file));
    }

    @RequestMapping(value = "/create-post-by-id", consumes = {"multipart/form-data"})
    public CommonResponseDto<PostDto> createPostById(@RequestParam(value = "image", required = false) MultipartFile file,
                                                     @RequestParam("id") Long id,
                                                 @RequestParam("title") String title,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("imageUrl") String imageUrl) throws IOException {

        return new CommonResponseDto<>(postService.createPostById(id, title, description, imageUrl, file));
    }


    @GetMapping("/{id}")
    public CommonResponseDto<PostDto> getPostById(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(new PostDto(postService.getPostById(id)));
    }

    @PutMapping("/{id}")
    public CommonResponseDto<String> editPost(@PathVariable Long id,
                                              @RequestParam(value = "image", required = false) MultipartFile file,
                                              @RequestParam("title") String title,
                                              @RequestParam("imageUrl") String imageUrl) throws IOException {

        return new CommonResponseDto<>(postService.editPost(id, title, imageUrl, file));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deletePost(@PathVariable Long id) {
        return new CommonResponseDto<>(postService.deletePost(id));
    }
}
