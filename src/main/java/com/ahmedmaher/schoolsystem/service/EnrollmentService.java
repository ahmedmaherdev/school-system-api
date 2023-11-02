package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.EnrollmentDTO;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.model.Classroom;
import com.ahmedmaher.schoolsystem.model.Enrollment;
import com.ahmedmaher.schoolsystem.model.School;
import com.ahmedmaher.schoolsystem.model.User;
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
    private EnrollmentRepository enrollmentRepository;

    private UserRepository userRepository;
    private ClassroomRepository classroomRepository;
    private SchoolRepository schoolRepository;

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
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        User student = this.userRepository.findById(enrollmentDTO.getStudentId()).orElse(null);
        if(student == null) throw new NotFoundException("User is not found");

        School school = this.schoolRepository.findById(enrollmentDTO.getSchoolId()).orElse(null);
        if(school == null) throw new NotFoundException("School is not found");

        Classroom classroom = this.classroomRepository.findClassroomByIdAndSchoolId(
                enrollmentDTO.getClassroomId(),
                enrollmentDTO.getSchoolId()
        );

        if(classroom == null) throw new NotFoundException("Classroom is not found");

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setClassroom(classroom);
        enrollment.setSchool(school);
        enrollment.setCreatedAt(LocalDateTime.now());
        enrollment.setUpdatedAt(LocalDateTime.now());

        classroom.setCapacity(classroom.getCapacity() + 1);
        try{
            this.enrollmentRepository.save(enrollment);
            this.classroomRepository.save(classroom);
        }catch (DataIntegrityViolationException ex) {
            throw new DuplicatedException("Student enrolled already to this classroom.");
        }
        return Mapper.mapEnrollmentToEnrollmentDTO(enrollment);
    }
}
