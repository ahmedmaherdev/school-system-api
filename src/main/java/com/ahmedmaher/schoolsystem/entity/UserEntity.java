package com.ahmedmaher.schoolsystem.entity;

import com.ahmedmaher.schoolsystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user" , uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    private String name;
    private String password;
    private String photo;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
         this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
         this.updatedAt = updatedAt;
    }
}