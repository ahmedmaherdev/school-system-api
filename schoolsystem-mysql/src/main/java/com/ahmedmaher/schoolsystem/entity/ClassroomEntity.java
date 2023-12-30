package com.ahmedmaher.schoolsystem.entity;

import com.ahmedmaher.schoolsystem.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classroom")
public class ClassroomEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(nullable = false , columnDefinition = "integer default 0")
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    @ManyToMany(cascade = {CascadeType.MERGE , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name = "enrollment" ,joinColumns = @JoinColumn(name = "classroom_id") ,
            inverseJoinColumns = @JoinColumn(name= "user_id"))
    private List<UserEntity> users;

    public Long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public void addStudent(UserEntity student) {
        if(users == null) users = new ArrayList<>();
        users.add(student);
    }

    public void removeStudent(UserEntity student) {
        if(users == null) users = new ArrayList<>();
        users.remove(student);
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
