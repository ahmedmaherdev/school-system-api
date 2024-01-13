package com.ahmedmaher.schoolsystem.dto.auth;

import com.ahmedmaher.schoolsystem.dto.base.PasswordMatchingDTO;
import com.ahmedmaher.schoolsystem.validator.annotations.ValidPasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidPasswordMatches
public class SignupReqDTO extends PasswordMatchingDTO {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "username is required")
    @Size(min = 5, max = 60 , message = "username must more than 5 and less than 60.")
    private String username;

    @NotBlank(message = "email is required")
    @Email(message = "Please, provide a valid email.")
    @Size(min = 5, max = 60 , message = "email must more than 5 and less than 60.")
    private String email;
}
