package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    UserEntity findByUsername(String username);
}
