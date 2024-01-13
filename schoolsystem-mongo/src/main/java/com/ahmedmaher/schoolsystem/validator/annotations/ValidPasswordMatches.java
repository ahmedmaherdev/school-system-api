package com.ahmedmaher.schoolsystem.validator.annotations;


import com.ahmedmaher.schoolsystem.validator.validators.PasswordMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface ValidPasswordMatches {
    String message() default "Passwords are not the same.";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
