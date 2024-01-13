package com.ahmedmaher.schoolsystem.service.enrollment;

import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import com.ahmedmaher.schoolsystem.document.UserDoc;

import java.util.List;

public interface EnrollmentService {
    void createEnrollment(String userId, String classroomId);
    void deleteEnrollment(String userId, String classroomId);
    List<ClassroomDoc> getUserEnrollments(String userId);

    List<UserDoc> getClassroomUsers(String classroomId);
}
