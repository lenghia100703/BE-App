package com.mobileapp.backend.services;

import com.mobileapp.backend.configs.AcceptedFileType;
import com.mobileapp.backend.entities.NewsEntity;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

@Service
public class StorageService {
    private final String imageFolder = "images/";

    RandomStringGenerator randomStringGenerator =
            new RandomStringGenerator.Builder()
                    .withinRange('0', 'z')
                    .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                    .build();

    public String saveImage(MultipartFile file) {
        try {
            if (file == null || !AcceptedFileType.image.contains(file.getContentType())) {
                return null;
            }
            var is = file.getInputStream();
            Instant instant = Instant.now();
            StringBuilder pathToSave = new StringBuilder(imageFolder + instant + "_" + FilenameUtils.getBaseName(file.getOriginalFilename()));
            while (new File(pathToSave + "." + FilenameUtils.getExtension(file.getOriginalFilename())).isFile()) {
                pathToSave.append(randomStringGenerator.generate(3));
            }
            pathToSave.append(".").append(FilenameUtils.getExtension(file.getOriginalFilename()));
            pathToSave = new StringBuilder(pathToSave.toString().replaceAll("[\\\\:\\*\\?\\\"<>\\|]", ""));
            Files.copy(is, Paths.get(pathToSave.toString()), StandardCopyOption.REPLACE_EXISTING);
            return pathToSave.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getImage(NewsEntity news) {
        String path = news.getImage();
        if (path == null) return null;
        try {
            return Files.readAllBytes(new File(path).toPath());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
