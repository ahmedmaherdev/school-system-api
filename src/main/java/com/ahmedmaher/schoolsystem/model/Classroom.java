package com.ahmedmaher.schoolsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classroom")
public class Classroom extends BaseEntity {
    private String name;

    @Column(nullable = false , columnDefinition = "integer default 0")
    private int capacity;

    @ManyToOne
    private School school;
}
