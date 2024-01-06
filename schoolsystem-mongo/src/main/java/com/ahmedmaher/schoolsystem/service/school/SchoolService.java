package com.ahmedmaher.schoolsystem.service.school;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.SchoolDocument;
import com.ahmedmaher.schoolsystem.base.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolService extends BaseService<SchoolDocument> {
    long getAllSchoolsCount();

    List<ClassroomDocument> getSchoolClassrooms(String schoolId , Pageable pageable);
}
