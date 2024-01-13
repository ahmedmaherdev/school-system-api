package com.ahmedmaher.schoolsystem.validator.validators;

import com.ahmedmaher.schoolsystem.dto.base.PasswordMatchingDTO;
import com.ahmedmaher.schoolsystem.validator.annotations.ValidPasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<ValidPasswordMatches, PasswordMatchingDTO> {

    @Override
    public void initialize(ValidPasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PasswordMatchingDTO passwordMatchingDTO, ConstraintValidatorContext constraintValidatorContext) {
        return passwordMatchingDTO.getPassword().equals(passwordMatchingDTO.getPasswordConfirm());
    }
}
