package com.ahmedmaher.schoolsystem.dto.auth;

import com.ahmedmaher.schoolsystem.dto.base.PasswordMatchingDTO;
import com.ahmedmaher.schoolsystem.validator.annotations.ValidPasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ValidPasswordMatches
public class UpdatePasswordReqDTO extends PasswordMatchingDTO {
    @NotBlank(message = "current password is required")
    @Size(min = 8, max = 100 , message = "password must more than 8 and less than 100.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "current password must more than 8 characters and have at least lowercase, uppercase characters, and numbers.")
    private String oldPassword;

    public UpdatePasswordReqDTO(String oldPassword, String password, String passwordConfirm) {
        super(password , passwordConfirm);
        this.oldPassword = oldPassword;
    }
}
