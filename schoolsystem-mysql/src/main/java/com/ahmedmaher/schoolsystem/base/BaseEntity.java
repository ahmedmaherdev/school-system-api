package com.ahmedmaher.schoolsystem.base;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    protected LocalDateTime createdAt;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    protected LocalDateTime updatedAt;
}
