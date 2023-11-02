package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.model.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom , Long> {

    @Query("SELECT c FROM Classroom c WHERE c.school.id = :schoolId")
    Page<Classroom> findAllClassroomsBySchoolId(long schoolId, Pageable pageable);


    @Query("SELECT c FROM Classroom c WHERE c.id = :classroomId AND c.school.id = :schoolId")
    Classroom findClassroomByIdAndSchoolId(long classroomId , long schoolId);
    @Query("SELECT COUNT(*) FROM Classroom c WHERE c.school.id = :schoolId")
    long findAllCountBySchoolId(long schoolId);
}
