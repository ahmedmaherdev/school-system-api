package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.ClassroomDTO;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.model.Classroom;
import com.ahmedmaher.schoolsystem.model.School;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomService {
    private ClassroomRepository classroomRepository;
    private SchoolRepository schoolRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository , SchoolRepository schoolRepository){
        this.classroomRepository = classroomRepository;
        this.schoolRepository = schoolRepository;
    }

    public List<ClassroomDTO> getAllClassrooms( long schoolId) {
        List<Classroom> classrooms = this.classroomRepository.findAllClassroomsBySchoolId(schoolId);
        List<ClassroomDTO> classroomDTOs =  classrooms.stream().map(Mapper::mapClassroomToClassroomDTO).collect(Collectors.toList());
        return classroomDTOs;
    }

    public ClassroomDTO getClassroomById(Long classroomId) throws NotFoundException {
        Classroom classroom = this.classroomRepository.findById(classroomId).orElse(null);
        if(classroom == null) throw new NotFoundException("Classroom not found with id: " + classroomId);
        return Mapper.mapClassroomToClassroomDTO(classroom);
    }

    @Transactional
    public ClassroomDTO createClassroom(ClassroomDTO classroomDTO){
        School school = this.schoolRepository.findById(classroomDTO.getSchoolId()).orElse(null);
        if(school == null) throw new NotFoundException("School not found with id: " + classroomDTO.getSchoolId());

        Classroom classroom =  new Classroom();
        classroom.setName(classroomDTO.getName());
        classroom.setCapacity(classroom.getCapacity());
        classroom.setSchool(school);
        classroom.setCreatedAt(LocalDateTime.now());
        classroom.setUpdatedAt(LocalDateTime.now());

        this.classroomRepository.save(classroom);
        return Mapper.mapClassroomToClassroomDTO(classroom);
    }

    @Transactional
    public ClassroomDTO updateClassroom(long classroomId, ClassroomDTO classroomDTO) throws NotFoundException{
        Classroom selectedClassroom = this.classroomRepository.findById(classroomId).orElse(null);
        if(selectedClassroom == null) throw new NotFoundException("Classroom not found with id: " + classroomId);

        School school = this.schoolRepository.findById(classroomDTO.getSchoolId()).orElse(null);
        if(school == null) throw new NotFoundException("School not found with id: " + classroomDTO.getSchoolId());

        selectedClassroom.setName(classroomDTO.getName());
        selectedClassroom.setCapacity(classroomDTO.getCapacity());
        selectedClassroom.setSchool(school);
        selectedClassroom.setUpdatedAt(LocalDateTime.now());

        return Mapper.mapClassroomToClassroomDTO(selectedClassroom);
    }

    @Transactional
    public boolean deleteClassroom(long classroomId) throws NotFoundException{
        Classroom deletedClassroom = this.classroomRepository.findById(classroomId).orElse(null);
        if(deletedClassroom == null) throw new NotFoundException("Classroom not found with id: " + classroomId);
        this.classroomRepository.delete(deletedClassroom);
        return true;
    }

    public long getAllClassroomsCount(long schoolId) {
        return this.classroomRepository.findAllCountBySchoolId(schoolId);
    }
}
