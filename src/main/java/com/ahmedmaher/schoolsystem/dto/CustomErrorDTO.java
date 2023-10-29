package com.ahmedmaher.schoolsystem.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorDTO {
    private String message;
    private String status;
    private long timestamp;
}
