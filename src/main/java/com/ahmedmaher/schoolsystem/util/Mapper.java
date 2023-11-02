package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.EnrollmentResponseDTO;
import com.ahmedmaher.schoolsystem.dto.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.dto.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {
    public static UserResponseDTO mapUserToUserDTO(UserEntity userEntity){
        return new UserResponseDTO(userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername() ,
                userEntity.getEmail() ,
                Mapper.mapUserRoles(userEntity.getRoleEntities()),
                userEntity.getPhoto(),
                userEntity.getCreatedAt() ,
                userEntity.getUpdatedAt()
        );
    }
    public static SchoolResponseDTO mapSchoolToSchoolDTO(SchoolEntity schoolEntity){
        return new SchoolResponseDTO(schoolEntity.getId(),
                schoolEntity.getName(),
                schoolEntity.getAddress(),
                schoolEntity.getCreatedAt(),
                schoolEntity.getUpdatedAt()
        );
    }

    public static ClassroomResponseDTO mapClassroomToClassroomDTO(ClassroomEntity classroomEntity) {
        return new ClassroomResponseDTO(classroomEntity.getId(),
                classroomEntity.getName(),
                classroomEntity.getCapacity(),
                classroomEntity.getSchoolEntity().getId() ,
                classroomEntity.getCreatedAt(),
                classroomEntity.getUpdatedAt());
    }

    public static EnrollmentResponseDTO mapEnrollmentToEnrollmentDTO(EnrollmentEntity enrollmentEntity) {
        return new EnrollmentResponseDTO(
                enrollmentEntity.getId(),
                enrollmentEntity.getStudent().getId() ,
                enrollmentEntity.getClassroomEntity().getId() ,
                enrollmentEntity.getSchoolEntity().getId(),
                enrollmentEntity.getCreatedAt(),
                enrollmentEntity.getUpdatedAt());
    }

    public static Collection<SimpleGrantedAuthority> mapRolesSetToGrantedAuthority(Set<RoleEntity> userRoleEntities){
        Set<String> roles = mapUserRoles(userRoleEntities);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
    public static Set<String> mapUserRoles(Set<RoleEntity> roleEntities) {
        return roleEntities.stream().map(userRoleEntity -> userRoleEntity.getName()).collect(Collectors.toSet());
    }
}
