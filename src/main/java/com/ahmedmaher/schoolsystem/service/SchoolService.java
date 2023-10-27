package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
    private SchoolRepository schoolRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository){
        this.schoolRepository = schoolRepository;
    }
}
