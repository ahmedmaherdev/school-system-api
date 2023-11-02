package com.ahmedmaher.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollment" , uniqueConstraints = @UniqueConstraint(columnNames = {
        "student_id" , "classroom_id"
}))
public class EnrollmentEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroomEntity;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity schoolEntity;

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
