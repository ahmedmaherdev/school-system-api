package com.ahmedmaher.schoolsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRequestDTO {
    @NotBlank(message = "name is required.")
    private String name;

    @NotBlank(message = "address is required.")
    private String address;
}
