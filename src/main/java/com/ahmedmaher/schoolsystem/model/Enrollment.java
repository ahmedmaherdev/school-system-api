package com.ahmedmaher.schoolsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollment" , uniqueConstraints = @UniqueConstraint(columnNames = {
        "user_id" , "classroom_id"
}))
public class Enrollment extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
