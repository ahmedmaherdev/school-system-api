package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.EnrollmentRequestDTO;
import com.ahmedmaher.schoolsystem.dto.EnrollmentResponseDTO;
import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.EnrollmentEntity;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import com.ahmedmaher.schoolsystem.repository.EnrollmentRepository;
import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             UserRepository userRepository,
                             ClassroomRepository classroomRepository,
                             SchoolRepository schoolRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.schoolRepository = schoolRepository;
    }

    @Transactional
    public EnrollmentResponseDTO createEnrollment(long schoolId, EnrollmentRequestDTO enrollmentRequestDTO) {
        UserEntity studentEntity = this.userRepository.findById(enrollmentRequestDTO.getStudentId()).orElse(null);
        if(studentEntity == null) throw new NotFoundException("User is not found");

        SchoolEntity schoolEntity = this.schoolRepository.findById(schoolId).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School is not found");

        ClassroomEntity classroomEntity = this.classroomRepository.findClassroomByIdAndSchoolId(
                enrollmentRequestDTO.getClassroomId(),
                schoolId
        );

        if(classroomEntity == null) throw new NotFoundException("Classroom is not found");

        EnrollmentEntity enrollmentEntity = new EnrollmentEntity();
        enrollmentEntity.setStudent(studentEntity);
        enrollmentEntity.setClassroomEntity(classroomEntity);
        enrollmentEntity.setSchoolEntity(schoolEntity);
        enrollmentEntity.setCreatedAt(LocalDateTime.now());
        enrollmentEntity.setUpdatedAt(LocalDateTime.now());

        classroomEntity.setCapacity(classroomEntity.getCapacity() + 1);
        try{
            this.enrollmentRepository.save(enrollmentEntity);
            this.classroomRepository.save(classroomEntity);
        }catch (DataIntegrityViolationException ex) {
            throw new DuplicatedException("Student enrolled already to this classroom.");
        }
        return Mapper.mapEnrollmentToEnrollmentDTO(enrollmentEntity);
    }
}
