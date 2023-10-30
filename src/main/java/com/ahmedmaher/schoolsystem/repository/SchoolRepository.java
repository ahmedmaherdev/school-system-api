package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.model.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School , Long> {

}
