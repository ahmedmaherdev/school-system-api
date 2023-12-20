package com.ahmedmaher.schoolsystem.controller;


import com.ahmedmaher.schoolsystem.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticResourceController {

    private final FileUploadService fileUploadService;

    @Autowired

    public StaticResourceController(FileUploadService fileUploadService) {

        this.fileUploadService = fileUploadService;
    }


    @GetMapping("${app.config.backend.resource.user}")
    public ResponseEntity<Resource> getUserResource(@PathVariable String filename) {
        Resource resource = fileUploadService.loadUserPhoto(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
