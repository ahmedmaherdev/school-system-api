package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {
}
