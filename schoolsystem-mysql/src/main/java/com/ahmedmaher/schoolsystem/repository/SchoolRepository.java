package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
    @Query("SELECT s FROM SchoolEntity s WHERE s.name LIKE %:word% ORDER BY s.name")
    List<SchoolEntity> searchBy(String word, Pageable pageable);

    @Query("SELECT s FROM SchoolEntity s JOIN FETCH s.classrooms WHERE s.id = :schoolId")
    SchoolEntity findSchoolWithClassrooms(long schoolId);
}
