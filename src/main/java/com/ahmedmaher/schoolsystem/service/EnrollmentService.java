package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.EnrollmentDTO;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.model.Classroom;
import com.ahmedmaher.schoolsystem.model.Enrollment;
import com.ahmedmaher.schoolsystem.model.User;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import com.ahmedmaher.schoolsystem.repository.EnrollmentRepository;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;

    private UserRepository userRepository;
    private ClassroomRepository classroomRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, ClassroomRepository classroomRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
    }

    @Transactional
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        User student = this.userRepository.findById(enrollmentDTO.getStudentId()).orElse(null);
        if(student == null) throw new NotFoundException("User is not found");

        Classroom classroom = this.classroomRepository.findById(enrollmentDTO.getClassroomId()).orElse(null);
        if(classroom == null) throw new NotFoundException("Classroom is not found");

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setClassroom(classroom);
        enrollment.setCreatedAt(LocalDateTime.now());
        enrollment.setUpdatedAt(LocalDateTime.now());

        try{
            this.enrollmentRepository.save(enrollment);
        }catch (DataIntegrityViolationException ex) {
            throw new DuplicatedException("Student enrolled already to this classroom.");
        }
        return Mapper.mapEnrollmentToEnrollmentDTO(enrollment);
    }
}
