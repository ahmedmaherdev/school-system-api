package com.ahmedmaher.schoolsystem.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordReqDTO {
    @NotBlank(message = "password is required")
    @Size(min = 8, max = 100 , message = "password must more than 8 and less than 100.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "password must more than 8 characters and have at least lowercase, uppercase characters, and numbers.")
    private String password;

    @NotBlank(message = "password confirm is required")
    @Size(min = 8, max = 100 , message = "password confirm must more than 8 and less than 100.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "password confirm must more than 8 characters and have at least lowercase, uppercase characters, and numbers.")
    private String passwordConfirm;
}
