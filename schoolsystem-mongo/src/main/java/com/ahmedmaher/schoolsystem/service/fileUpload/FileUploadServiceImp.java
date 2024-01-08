package com.ahmedmaher.schoolsystem.service.fileUpload;

import com.ahmedmaher.schoolsystem.exception.BadRequestException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadServiceImp implements FileUploadService {
    private final List<String> SUPPORTED_PHOTO_FORMAT
            = Arrays.asList("image/jpg" , "image/jpeg" , "image/png" , "image/webp");

    @Value("${file.upload.base-dir}")
    private String uploadDir;

    @Value("${file.upload.user.dir}")
    private String userUploadDir;

    public String saveUserPhoto(MultipartFile file) throws IOException {
        if(file == null || file.isEmpty() || !SUPPORTED_PHOTO_FORMAT.contains(file.getContentType()))
            throw new BadRequestException("Not supported photo format.");
        return saveFile(userUploadDir , file);
    }

    public Resource loadUserPhoto(String fileName) {
        return loadFile(userUploadDir , fileName);
    }

    public MediaType getPhotoMediaType(String fileName) {
        String fileExtension = getFileExtension(fileName);
        return MediaType.parseMediaType("image/" + fileExtension);
    }

    private Resource loadFile(String folderName , String fileName) {
        try {
            Path path = Paths.get(uploadDir , folderName).resolve(fileName).normalize();
            Resource resource = new UrlResource(path.toUri());

            if(!resource.exists())
                throw new NotFoundException("File not found.");
            return resource;
        }catch (Exception ex) {
            throw new NotFoundException("File not found.");
        }

    }
    private String saveFile(String folderName , MultipartFile file) throws IOException {
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
