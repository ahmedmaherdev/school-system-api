package com.ahmedmaher.schoolsystem.dto.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @Size(min = 5, max = 60 , message = "username must more than 5 and less than 60.")
    @NotBlank(message = "username is required.")
    private String username;

    @Size(min = 8, max = 100 , message = "password must more than 5 and less than 60.")
    @NotBlank(message = "password is required.")
    private String password;
}
