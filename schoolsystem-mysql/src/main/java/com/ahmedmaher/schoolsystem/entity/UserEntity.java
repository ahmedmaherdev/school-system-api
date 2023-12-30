package com.ahmedmaher.schoolsystem.entity;

import com.ahmedmaher.schoolsystem.base.BaseEntity;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_detail_id")
    private StudentDetailEntity studentDetailEntity;

    @ManyToMany(cascade = {CascadeType.MERGE , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinTable(name = "enrollment" ,joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "classroom_id"))
    private List<ClassroomEntity> classrooms;


    public Long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void addClassroom(ClassroomEntity classroom) {
        if(classrooms == null) classrooms = new ArrayList<>();
        classrooms.add(classroom);
    }

    public void removeClassroom(ClassroomEntity classroom) {
        classrooms.remove(classroom);
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
