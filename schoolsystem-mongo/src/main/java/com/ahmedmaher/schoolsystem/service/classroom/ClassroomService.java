package com.ahmedmaher.schoolsystem.service.classroom;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.base.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassroomService extends BaseService<ClassroomDocument> {
    List<ClassroomDocument> getAllBySchoolId(String schoolId , Pageable pageable);

    List<UserDocument> getAllStudentsByClassroomId(String classroomId);

    long getAllClassroomsCount(String schoolId);
}
