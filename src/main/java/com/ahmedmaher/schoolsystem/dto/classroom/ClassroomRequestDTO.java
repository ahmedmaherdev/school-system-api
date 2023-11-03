package com.ahmedmaher.schoolsystem.dto.classroom;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomRequestDTO {
    @NotBlank(message = "name is required.")
    private String name;
}
