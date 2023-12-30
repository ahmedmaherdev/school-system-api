package com.ahmedmaher.schoolsystem.dto.classroom;

import com.ahmedmaher.schoolsystem.dto.school.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomResponseDTO {
    private String id;
    private String name;
    private int capacity;
    private SchoolResponseDTO school;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
