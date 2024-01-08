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


public interface FileUploadService {
    String saveUserPhoto(MultipartFile file) throws IOException;
    Resource loadUserPhoto(String fileName);
    MediaType getPhotoMediaType(String fileName);
}