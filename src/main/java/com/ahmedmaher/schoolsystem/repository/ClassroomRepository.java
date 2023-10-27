package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom , Long> {
}
