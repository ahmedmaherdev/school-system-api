package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.enrollment.EnrollmentResponseDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.*;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {
    public static UserResponseDTO mapUserToUserDTO(UserEntity userEntity){
        return new UserResponseDTO(userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername() ,
                userEntity.getEmail() ,
                mapUserRolesToStringSet(userEntity.getRoles()),
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

    public static List<SimpleGrantedAuthority> mapRolesToGrantedAuthority(Set<UserRole> userRoles){
        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.name())).collect(Collectors.toList());
    }

    public static Set<String> mapUserRolesToStringSet(Set<UserRole> userRoles) {
        return userRoles.stream().map(Enum::name).collect(Collectors.toSet());
    }
}
