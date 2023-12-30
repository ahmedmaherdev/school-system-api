package com.ahmedmaher.schoolsystem.service.classroom;

import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.base.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassroomService extends BaseService<ClassroomEntity> {
    List<ClassroomEntity> getAllBySchoolId(long schoolId , Pageable pageable);

    List<UserEntity> getAllStudentsByClassroomId(long classroomId);

    long getAllClassroomsCount(long schoolId);
}
