package com.ahmedmaher.schoolsystem.document;

import com.ahmedmaher.schoolsystem.base.BaseDocument;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@SuperBuilder
@Document(collection = "schools")
public class SchoolDocument extends BaseDocument {
    @TextIndexed
    private String name;

    private String address;
}
