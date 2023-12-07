package com.ahmedmaher.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "studentDetail")
public class StudentDetail extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;
}
