package com.ahmedmaher.schoolsystem.document;

import com.ahmedmaher.schoolsystem.base.BaseDocument;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
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

    private String passwordResetToken;

    private LocalDateTime passwordResetExpires;
    public void addClassroom(ClassroomDocument classroom) {
        if(classrooms == null) classrooms = new ArrayList<>();
        classrooms.add(classroom);
    }
    public void removeClassroom(ClassroomDocument classroom) {
        classrooms.remove(classroom);
    }
}
