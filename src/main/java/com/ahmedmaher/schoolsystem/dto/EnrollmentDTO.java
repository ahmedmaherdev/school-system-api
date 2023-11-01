package com.ahmedmaher.schoolsystem.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private long id;
    @NotNull(message = "studentId is required")
    private long studentId;
    @NotNull(message = "classroomId is required")
    private long classroomId;
    @NotNull(message = "schoolId is required")
    private long schoolId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
