package com.ahmedmaher.schoolsystem.dto.classroom;

import com.ahmedmaher.schoolsystem.dto.school.SchoolResDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomResDTO {
    private String id;
    private String name;
    private int capacity;
    private SchoolResDTO school;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
