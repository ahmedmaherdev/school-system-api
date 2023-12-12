package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.service.BaseService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends BaseService<UserEntity> {
    UserEntity getByUsername(String username) throws NotFoundException;

    List<UserEntity> search(String word, Pageable pageable);

    long getAllUsersCount();

    List<ClassroomEntity> getStudentEnrollments(long studentId);

    void createStudentEnrollment(long studentId, long classroomId);

    void deleteStudentEnrollment(long studentId, long classroomId);

}
