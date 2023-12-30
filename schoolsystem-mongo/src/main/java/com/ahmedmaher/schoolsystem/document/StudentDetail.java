package com.ahmedmaher.schoolsystem.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StudentDetail {

    @DBRef
    private SchoolDocument school;
}
