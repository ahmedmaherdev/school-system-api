package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {

    @Query("SELECT c FROM ClassroomEntity c WHERE c.school.id = :schoolId")
    Page<ClassroomEntity> findAllClassroomsBySchoolId(long schoolId, Pageable pageable);

    @Query("SELECT c FROM ClassroomEntity c WHERE c.id = :classroomId AND c.school.id = :schoolId")
    ClassroomEntity findClassroomByIdAndSchoolId(long classroomId , long schoolId);
    @Query("SELECT COUNT(*) FROM ClassroomEntity c WHERE c.school.id = :schoolId")
    long findAllCountBySchoolId(long schoolId);

    @Query("SELECT c FROM ClassroomEntity c WHERE c.name LIKE %:word% ORDER BY c.name")
    List<ClassroomEntity> searchBy(String word, Pageable pageable);

    @Query("SELECT c FROM ClassroomEntity c JOIN FETCH c.users WHERE c.id = :classroomId")
    ClassroomEntity findClassroomWithAllStudents(long classroomId);
}
