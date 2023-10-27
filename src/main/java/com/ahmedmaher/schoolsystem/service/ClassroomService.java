package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {
    private ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository){
        this.classroomRepository = classroomRepository;
    }
}
