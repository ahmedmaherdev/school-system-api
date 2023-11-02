package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

}
