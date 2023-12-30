package com.ahmedmaher.schoolsystem.service.enrollment;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;

import java.util.List;

public interface EnrollmentService {
    void createEnrollment(String userId, String classroomId);
    void deleteEnrollment(String userId, String classroomId);
    List<ClassroomDocument> getUserEnrollments(String userId);

}
