package com.ahmedmaher.schoolsystem.service.school;

import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import com.ahmedmaher.schoolsystem.document.SchoolDoc;
import com.ahmedmaher.schoolsystem.base.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolService extends BaseService<SchoolDoc> {
    long getAllSchoolsCount();

    List<ClassroomDoc> getSchoolClassrooms(String schoolId , Pageable pageable);
}
