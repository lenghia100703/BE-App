package com.mobileapp.backend.controllers;

import com.mobileapp.backend.repositories.PostRepository;
import com.mobileapp.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;


}
