package com.ahmedmaher.schoolsystem.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;
}
