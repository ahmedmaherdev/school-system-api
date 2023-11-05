package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.enrollment.EnrollmentRequestDTO;
import com.ahmedmaher.schoolsystem.dto.enrollment.EnrollmentResponseDTO;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.service.EnrollmentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;

@RestController
@RequestMapping("${app.config.backend.enrollment.base-uri}")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @RolesAllowed(UserRole.Names.ADMIN)
    @PostMapping("${app.config.backend.enrollment.api.create-enrollment-uri}")
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment(
            @Valid @RequestBody EnrollmentRequestDTO enrollmentRequestDTO,
            @PathVariable("schoolId") long schoolId
    ) {
        EnrollmentResponseDTO createdEnrollment = this.enrollmentService.createEnrollment(schoolId ,enrollmentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnrollment);
    }


}
