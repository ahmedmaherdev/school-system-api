package com.ahmedmaher.schoolsystem.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolResponseDTO {
    private long id;

    @NotBlank(message = "name is required.")
    private String name;

    @NotBlank(message = "address is required.")
    private String address;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
