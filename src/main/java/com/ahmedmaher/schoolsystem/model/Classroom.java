package com.ahmedmaher.schoolsystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classroom")
public class Classroom extends BaseEntity {
    private String name;

    @Column(nullable = false , columnDefinition = "integer default 0")
    private int capacity;

    @ManyToOne
    private School school;

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
