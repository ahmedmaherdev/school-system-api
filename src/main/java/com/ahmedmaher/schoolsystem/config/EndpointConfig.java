package com.ahmedmaher.schoolsystem.config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointConfig {
    public static final String AUTH = "/api/auth";
    public static final String USER = "/api/users";
    public static final String USER_ID = USER + "/{userId}";
    public static final String SCHOOL = "/api/schools";
    public static final String SCHOOL_ID = SCHOOL + "/{schoolId}";

    public static final String CLASSROOM = "/api/schools/{schoolId}/classrooms";
    public static final String CLASSROOM_ID = CLASSROOM + "/{classroomId}";
    public static final String ENROLLMENT = "/api/schools/{schoolId}/enrollments";
    public static final String ENROLLMENT_ID = ENROLLMENT + "/{enrollmentId}";

}
