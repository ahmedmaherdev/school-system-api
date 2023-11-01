package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.ClassroomDTO;
import com.ahmedmaher.schoolsystem.dto.EnrollmentDTO;
import com.ahmedmaher.schoolsystem.dto.SchoolDTO;
import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.model.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {
    public static UserDTO mapUserToUserDTO(User user){
        return new UserDTO(user.getId(),
                user.getName(),
                user.getUsername() ,
                user.getEmail() ,
                Mapper.mapUserRoles(user.getRoles()),
                user.getCreatedAt() ,
                user.getUpdatedAt()
        );
    }
    public static SchoolDTO mapSchoolToSchoolDTO(School school){
        return new SchoolDTO(school.getId(),
                school.getName(),
                school.getAddress(),
                school.getCreatedAt(),
                school.getUpdatedAt()
        );
    }

    public static ClassroomDTO mapClassroomToClassroomDTO(Classroom classroom) {
        return new ClassroomDTO(classroom.getId(),
                classroom.getName(),
                classroom.getCapacity(),
                classroom.getSchool().getId() ,
                classroom.getCreatedAt(),
                classroom.getUpdatedAt());
    }

    public static EnrollmentDTO mapEnrollmentToEnrollmentDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudent().getId() ,
                enrollment.getClassroom().getId() ,
                enrollment.getCreatedAt(),
                enrollment.getUpdatedAt());
    }

    public static Collection<SimpleGrantedAuthority> mapRolesSetToGrantedAuthority(Set<Role> userRoles){
        Set<String> roles = mapUserRoles(userRoles);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
    public static Set<String> mapUserRoles(Set<Role> roles) {
        return roles.stream().map(userRole -> userRole.getName()).collect(Collectors.toSet());
    }
}
