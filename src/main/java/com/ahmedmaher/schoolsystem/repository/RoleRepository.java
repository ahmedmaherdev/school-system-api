package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r WHERE r.name = :name")
    RoleEntity getRoleByName(String name);
}
