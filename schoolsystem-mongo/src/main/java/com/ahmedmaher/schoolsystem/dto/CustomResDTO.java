package com.ahmedmaher.schoolsystem.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResDTO<T> {
    private T data;
    private int count = 0;
    private long allCount = 0;
}
