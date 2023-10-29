package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom , Long> {

    @Query("SELECT c FROM Classroom c WHERE c.school.id = :schoolId")
    List<Classroom> findAllClassroomsBySchoolId(long schoolId);

    @Query("SELECT COUNT(*) FROM Classroom c WHERE c.school.id = :schoolId")
    long findAllCountBySchoolId(long schoolId);
}
