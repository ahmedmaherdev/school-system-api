package com.ahmedmaher.schoolsystem.document;

import com.ahmedmaher.schoolsystem.base.BaseDocument;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "users")
public class UserDocument extends BaseDocument {

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    @TextIndexed
    private String name;
    private String password;
    private String photo;

    private Set<UserRole> roles;

    private StudentDetail student;

    @DBRef(lazy = true)
    private List<ClassroomDocument> classrooms;


    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void addClassroom(ClassroomDocument classroom) {
        if(classrooms == null) classrooms = new ArrayList<>();
        classrooms.add(classroom);
    }

    public void removeClassroom(ClassroomDocument classroom) {
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
