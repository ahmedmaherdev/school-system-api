package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {

    @Query("SELECT c FROM ClassroomEntity c WHERE c.schoolEntity.id = :schoolId")
    Page<ClassroomEntity> findAllClassroomsBySchoolId(long schoolId, Pageable pageable);


    @Query("SELECT c FROM ClassroomEntity c WHERE c.id = :classroomId AND c.schoolEntity.id = :schoolId")
    ClassroomEntity findClassroomByIdAndSchoolId(long classroomId , long schoolId);
    @Query("SELECT COUNT(*) FROM ClassroomEntity c WHERE c.schoolEntity.id = :schoolId")
    long findAllCountBySchoolId(long schoolId);
}
