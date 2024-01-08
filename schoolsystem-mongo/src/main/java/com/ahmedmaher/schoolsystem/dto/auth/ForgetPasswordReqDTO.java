package com.ahmedmaher.schoolsystem.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordReqDTO {

    @NotBlank(message = "email is required")
    @Email(message = "Please, provide a valid email.")
    @Size(min = 5, max = 60 , message = "email must more than 5 and less than 60.")
    private String email;
}
