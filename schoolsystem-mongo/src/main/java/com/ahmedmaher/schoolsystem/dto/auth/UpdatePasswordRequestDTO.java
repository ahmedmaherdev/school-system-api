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
public class UpdatePasswordRequestDTO {
    @NotBlank(message = "current password is required")
    @Size(min = 8, max = 100 , message = "password must more than 8 and less than 100.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "current password must more than 8 characters and have at least lowercase, uppercase characters, and numbers.")
    private String password;

    @NotBlank(message = "new password is required")
    @Size(min = 8, max = 100 , message = "password must more than 8 and less than 100.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "new password must more than 8 characters and have at least lowercase, uppercase characters, and numbers.")
    private String newPassword;

    @NotBlank(message = "new password confirm is required")
    @Size(min = 8, max = 100 , message = "password confirm must more than 8 and less than 100.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "new password confirm must more than 8 characters and have at least lowercase, uppercase characters, and numbers.")
    private String newPasswordConfirm;
}
