package com.ahmedmaher.schoolsystem.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PhotoValidator implements ConstraintValidator<ValidPhoto , MultipartFile> {
    private final List<String> SUPPORTED_CONTENT_TYPES = Arrays.asList("image/jpg" , "image/jpeg" , "image/png" , "image/webp");
    @Override
    public void initialize(ValidPhoto constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if(file == null || file.isEmpty()) return false;
        return SUPPORTED_CONTENT_TYPES.contains(file.getContentType());
    }
}
