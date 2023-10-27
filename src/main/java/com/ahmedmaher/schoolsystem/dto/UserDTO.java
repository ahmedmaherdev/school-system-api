package com.ahmedmaher.schoolsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String name;
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
