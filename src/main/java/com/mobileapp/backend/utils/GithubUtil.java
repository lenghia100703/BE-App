package com.mobileapp.backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class GithubUtil {
    @Value("${github.repo}")
    private String repo;

    @Value("${github.owner}")
    private String owner;

    @Value("${github.token}")
    private String token;

    @Value("${github.baseUrl}")
    private String baseUrl;

    public String uploadImage(MultipartFile file, String namePath) throws IOException {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .build();
        System.out.println(token);
        byte[] imageData = file.getBytes();
        String base64ImageData = Base64.getEncoder().encodeToString(imageData);
        String path = namePath  + "/" + file.getOriginalFilename();
        String imageUrl = "https://raw.githubusercontent.com/" + owner + "/" + repo + "/main/" + path;

        Mono<String> res = webClient.method(HttpMethod.PUT)
                .uri("/repos/" + owner + "/" + repo + "/contents/" + path)
                .body(BodyInserters.fromValue(buildRequestBody(base64ImageData)))
                .retrieve().bodyToMono(String.class);
        res.subscribe(
                result -> System.out.println("Result: " + result),
                error -> System.err.println("Error: " + error.getMessage())
        );
        return imageUrl;
    }

    private  String buildRequestBody(String base64ImageData) {
        return "{\"message\": \"Upload image\", \"content\": \"" + base64ImageData + "\"}";
    }
}
