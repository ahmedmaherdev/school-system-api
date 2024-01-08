package com.ahmedmaher.schoolsystem.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDTO {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "username is required")
    @Size(min = 5, max = 60 , message = "username must more than 5 and less than 60.")
    private String username;

    @NotBlank(message = "email is required")
    @Email(message = "Please, provide a valid email.")
    @Size(min = 5, max = 60 , message = "email must more than 5 and less than 60.")
    private String email;


    @NotBlank(message = "password is required")
    @Size(min = 8, max = 100 , message = "password must more than 8 and less than 100.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "password must more than 8 characters and have at least lowercase, uppercase characters, and numbers.")
    private String password;

    private Set<String> roles;
}
