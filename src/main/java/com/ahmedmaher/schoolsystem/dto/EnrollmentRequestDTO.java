package com.ahmedmaher.schoolsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequestDTO {
    @NotNull(message = "studentId is required")
    private long studentId;
    @NotNull(message = "classroomId is required")
    private long classroomId;
}
