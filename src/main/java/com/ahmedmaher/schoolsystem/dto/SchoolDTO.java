package com.ahmedmaher.schoolsystem.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {
    private long id;

    @NotBlank(message = "name is required.")
    private String name;

    private String address;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
