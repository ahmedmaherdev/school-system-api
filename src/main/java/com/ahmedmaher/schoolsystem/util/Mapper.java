package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.enrollment.EnrollmentResponseDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.*;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class Mapper {
    public static UserResponseDTO mapUserToUserDTO(UserEntity userEntity){
        return new UserResponseDTO(userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername() ,
                userEntity.getEmail() ,
                userEntity.getRole(),
                userEntity.getPhoto(),
                userEntity.getCreatedAt() ,
                userEntity.getUpdatedAt()
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

    public static SimpleGrantedAuthority mapRoleToGrantedAuthority(UserRole userRole){
        return new SimpleGrantedAuthority(userRole.name());
    }
}
