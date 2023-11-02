package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment , Long> {

}
