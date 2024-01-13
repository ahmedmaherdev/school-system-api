package com.ahmedmaher.schoolsystem.service.classroom;

import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import com.ahmedmaher.schoolsystem.base.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassroomService extends BaseService<ClassroomDoc> {
    List<ClassroomDoc> getAllBySchoolId(String schoolId , Pageable pageable);

    long getAllClassroomsCount(String schoolId);
}
