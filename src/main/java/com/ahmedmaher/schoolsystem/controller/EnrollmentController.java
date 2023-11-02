package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.EnrollmentRequestDTO;
import com.ahmedmaher.schoolsystem.dto.EnrollmentResponseDTO;
import com.ahmedmaher.schoolsystem.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools/{schoolId}/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")

    @PostMapping("/")
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment(@Valid @RequestBody EnrollmentRequestDTO enrollmentRequestDTO,
                                                                  @PathVariable("schoolId") long schoolId
    ) {
        EnrollmentResponseDTO createdEnrollment = this.enrollmentService.createEnrollment(schoolId ,enrollmentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnrollment);
    }


}
