package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository){
        this.enrollmentRepository = enrollmentRepository;
    }
}
