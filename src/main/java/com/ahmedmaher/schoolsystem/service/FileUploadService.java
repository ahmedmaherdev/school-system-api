package com.ahmedmaher.schoolsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload.base-dir}")
    private String uploadDir;

    public String saveFile(String folderName , MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = Paths.get(uploadDir , folderName , fileName);
        try {
            Files.write(filePath , file.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException("Something went wrong in upload.");
        }
        return fileName;
    }

    private String generateUniqueFileName(String originalFileName){
        String fileExtension = getFileExtension(originalFileName);
        String randomUUID = UUID.randomUUID().toString().substring(0,8);
        long timestamp = System.currentTimeMillis();

        return randomUUID + timestamp + "." + fileExtension;
    }

    private String getFileExtension(String fileName) {
        String[] strs = fileName.split("\\.");
        return strs[strs.length-1];
    }
}
