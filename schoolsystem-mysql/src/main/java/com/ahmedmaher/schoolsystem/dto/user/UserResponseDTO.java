package com.ahmedmaher.schoolsystem.dto.user;

import com.ahmedmaher.schoolsystem.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Set<String> roles;
    private String photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
