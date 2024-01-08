package com.ahmedmaher.schoolsystem.dto.school;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolReqDTO {
    @NotBlank(message = "name is required.")
    private String name;

    @NotBlank(message = "address is required.")
    private String address;
}
