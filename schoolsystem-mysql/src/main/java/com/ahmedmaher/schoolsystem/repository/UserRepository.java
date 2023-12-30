package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    UserEntity findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.name LIKE %:word% ORDER BY u.name")
    List<UserEntity> searchBy(String word, Pageable pageable);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.classrooms WHERE u.id = :studentId")
    UserEntity findStudentWithEnrollments(long studentId);

}
