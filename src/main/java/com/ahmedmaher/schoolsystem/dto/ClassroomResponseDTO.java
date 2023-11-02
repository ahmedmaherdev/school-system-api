package com.ahmedmaher.schoolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomResponseDTO {
    private long id;
    private String name;
    private int capacity;
    private long schoolId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
