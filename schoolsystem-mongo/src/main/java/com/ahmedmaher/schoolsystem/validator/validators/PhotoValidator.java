package com.ahmedmaher.schoolsystem.validator.validators;


import com.ahmedmaher.schoolsystem.validator.annotations.ValidPhoto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class PhotoValidator implements ConstraintValidator<ValidPhoto, MultipartFile> {
    private final List<String> SUPPORTED_CONTENT_TYPES = Arrays.asList("image/jpg" , "image/jpeg" , "image/png" , "image/webp");
    @Override
    public void initialize(ValidPhoto constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if(file == null || file.isEmpty()) return false;
        return SUPPORTED_CONTENT_TYPES.contains(file.getContentType());
    }
}
