package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User , Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);
}
