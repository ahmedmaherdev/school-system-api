package com.ahmedmaher.schoolsystem.dto.user;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    private Set<String> roles;
    private String photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
