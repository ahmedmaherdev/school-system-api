package com.ahmedmaher.schoolsystem.validator.annotations;

import com.ahmedmaher.schoolsystem.validator.validators.PhotoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhotoValidator.class)
public @interface ValidPhoto {

    String message() default "Not supported photo format.";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
