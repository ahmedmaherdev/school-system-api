package com.ahmedmaher.schoolsystem.document;

import com.ahmedmaher.schoolsystem.base.BaseDocument;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;


@Data
@SuperBuilder
@Document(collection = "classrooms")
public class ClassroomDocument extends BaseDocument {

    @TextIndexed
    private String name;

    private int capacity = 0;

    @DBRef
    private SchoolDocument school;

    @DBRef(lazy = true)
    private List<UserDocument> users;
    public void addUser(UserDocument user) {
        if(users == null) users = new ArrayList<>();
        users.add(user);
    }

    public void removeUser(UserDocument user) {
        if(users == null) users = new ArrayList<>();
        users.remove(user);
    }

}
