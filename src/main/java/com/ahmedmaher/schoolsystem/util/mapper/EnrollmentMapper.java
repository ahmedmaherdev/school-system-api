package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.enrollment.EnrollmentResponseDTO;
import com.ahmedmaher.schoolsystem.entity.*;

public class EnrollmentMapper {

    public static EnrollmentResponseDTO mapEnrollmentToEnrollmentDTO(EnrollmentEntity enrollmentEntity) {
        return new EnrollmentResponseDTO(
                enrollmentEntity.getId(),
                enrollmentEntity.getStudent().getId() ,
                enrollmentEntity.getClassroomEntity().getId() ,
                enrollmentEntity.getSchoolEntity().getId(),
                enrollmentEntity.getCreatedAt(),
                enrollmentEntity.getUpdatedAt());
    }

}
