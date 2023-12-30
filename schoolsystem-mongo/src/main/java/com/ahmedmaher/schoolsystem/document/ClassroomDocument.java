package com.ahmedmaher.schoolsystem.document;

import com.ahmedmaher.schoolsystem.base.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "classrooms")
public class ClassroomDocument extends BaseDocument {

    @TextIndexed
    private String name;

    private int capacity = 0;

    @DBRef
    private SchoolDocument school;

    @DBRef(lazy = true)
    private List<UserDocument> users;

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public void addUser(UserDocument user) {
        if(users == null) users = new ArrayList<>();
        users.add(user);
    }

    public void removeUser(UserDocument user) {
        if(users == null) users = new ArrayList<>();
        users.remove(user);
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
