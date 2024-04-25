package com.mobileapp.backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GithubUtil {
    private static String repo = "Storage_Mobile_App";

    private static String owner = "lenghia100703";

    private static String token = "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";

    public static String uploadImage(MultipartFile file) throws IOException {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .build();

        byte[] imageData = file.getBytes();
        String base64ImageData = Base64.getEncoder().encodeToString(imageData);
        String path = "news/" + file.getOriginalFilename();
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

    private static String buildRequestBody(String base64ImageData) {
        return "{\"message\": \"Upload image\", \"content\": \"" + base64ImageData + "\"}";
    }
}
