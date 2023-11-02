package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

}
