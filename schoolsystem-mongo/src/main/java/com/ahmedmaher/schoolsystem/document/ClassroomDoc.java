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
@NoArgsConstructor
@Document(collection = "classrooms")
public class ClassroomDoc extends BaseDocument {

    @TextIndexed
    private String name;

    private int capacity = 0;

    @DBRef
    private SchoolDoc school;

    @DBRef(lazy = true)
    private List<UserDoc> users;
    public void addUser(UserDoc user) {
        if(users == null) users = new ArrayList<>();
        users.add(user);
    }

    public void removeUser(UserDoc user) {
        users.remove(user);
    }

}
