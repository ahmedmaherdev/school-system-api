package com.ahmedmaher.schoolsystem.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    protected LocalDateTime createdAt;

    @CreatedDate
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    protected LocalDateTime updatedAt;
}
