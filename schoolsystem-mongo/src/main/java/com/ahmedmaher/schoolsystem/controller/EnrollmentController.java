package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.service.enrollment.EnrollmentService;
import com.ahmedmaher.schoolsystem.util.mapper.ClassroomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.config.backend.enrollment.base-uri}")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("${app.config.backend.enrollment.api.load-all-enrollments-by-user-uri}")
    public ResponseEntity<?> getAllStudentEnrollments(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(
                ClassroomMapper.mapToClassroomResponseDTOs(
                        enrollmentService.getUserEnrollments(userId)
                )
        );
    }
    @PostMapping("${app.config.backend.enrollment.api.create-user-enrollment-uri}")
    public ResponseEntity<?> createStudentEnrollment(
            @PathVariable("userId") String userId,
            @PathVariable("classroomId") String classroomId
    ) {
        enrollmentService.createEnrollment(userId , classroomId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("${app.config.backend.enrollment.api.remove-user-enrollment-uri}")
    public ResponseEntity<?> deleteStudentEnrollment(
            @PathVariable("userId") String userId,
            @PathVariable("classroomId") String classroomId
    ) {
        enrollmentService.deleteEnrollment(userId , classroomId);
        return ResponseEntity.noContent().build();
    }
}
