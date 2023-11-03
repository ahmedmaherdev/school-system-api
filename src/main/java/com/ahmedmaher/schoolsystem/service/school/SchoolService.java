package com.ahmedmaher.schoolsystem.service.school;

import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import com.ahmedmaher.schoolsystem.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface SchoolService extends BaseService<SchoolEntity> {
    long getAllSchoolsCount();
}
