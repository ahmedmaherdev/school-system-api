package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.base.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends BaseService<UserEntity> {
    UserEntity getByUsername(String username) throws NotFoundException;

    List<UserEntity> search(String word, Pageable pageable);

    long getAllUsersCount();

    List<ClassroomEntity> getStudentEnrollments(long userId);

    void createStudentEnrollment(long userId, long classroomId);

    void deleteStudentEnrollment(long userId, long classroomId);

    UserEntity updateUserPhoto(UserEntity user, MultipartFile photoFile) throws Exception;
}
