package com.ahmedmaher.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "school")
public class SchoolEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ClassroomEntity> classrooms;

    public void addClassroom(ClassroomEntity classroom) {
        if(classrooms == null) classrooms = new ArrayList<>();
        classroom.setSchool(this);
        classrooms.add(classroom);
    }

    public void removeClassroom(ClassroomEntity classroom){
        classroom.setSchool(null);
    }

    public void setId(long id) {
         this.id = id;
    }

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
