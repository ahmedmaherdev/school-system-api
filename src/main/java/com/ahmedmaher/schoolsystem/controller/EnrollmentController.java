package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.EnrollmentDTO;
import com.ahmedmaher.schoolsystem.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools/{schoolId}/enrollments")
public class EnrollmentController {
    private EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO ,
                                                          @PathVariable("schoolId") long schoolId
    ) {
        enrollmentDTO.setSchoolId(schoolId);
        EnrollmentDTO createdEnrollment = this.enrollmentService.createEnrollment(enrollmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnrollment);
    }


}
