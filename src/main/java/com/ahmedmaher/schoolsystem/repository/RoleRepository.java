package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role , Long> {

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role getRoleByName(String name);
}
