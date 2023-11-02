package com.ahmedmaher.schoolsystem.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDTO {
    private long id;
    private long studentId;
    private long classroomId;
    private long schoolId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
