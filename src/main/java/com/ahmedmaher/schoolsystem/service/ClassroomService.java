package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomRequestDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository , SchoolRepository schoolRepository){
        this.classroomRepository = classroomRepository;
        this.schoolRepository = schoolRepository;
    }

    public List<ClassroomResponseDTO> getAllClassrooms(long schoolId , Pageable pageable) {
        Page<ClassroomEntity> classrooms = this.classroomRepository.findAllClassroomsBySchoolId(schoolId , pageable);
        return classrooms.getContent().stream().map(Mapper::mapClassroomToClassroomDTO).collect(Collectors.toList());
    }

    public ClassroomResponseDTO getClassroomById(Long classroomId) throws NotFoundException {
        ClassroomEntity classroomEntity = this.classroomRepository.findById(classroomId).orElse(null);
        if(classroomEntity == null) throw new NotFoundException("Classroom not found with id: " + classroomId);
        return Mapper.mapClassroomToClassroomDTO(classroomEntity);
    }

    @Transactional
    public ClassroomResponseDTO createClassroom( long schoolId, ClassroomRequestDTO classroomRequestDTO){
        SchoolEntity schoolEntity = this.schoolRepository.findById(schoolId).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School not found with id: " + schoolId);

        ClassroomEntity classroomEntity =  new ClassroomEntity();
        classroomEntity.setName(classroomRequestDTO.getName());
        classroomEntity.setCapacity(0);
        classroomEntity.setSchoolEntity(schoolEntity);
        classroomEntity.setCreatedAt(LocalDateTime.now());
        classroomEntity.setUpdatedAt(LocalDateTime.now());

        this.classroomRepository.save(classroomEntity);
        return Mapper.mapClassroomToClassroomDTO(classroomEntity);
    }

    @Transactional
    public ClassroomResponseDTO updateClassroom(
            long classroomId,
            long schoolId,
            ClassroomRequestDTO classroomRequestDTO
    ) throws NotFoundException{
        ClassroomEntity selectedClassroomEntity = this.classroomRepository.findById(classroomId).orElse(null);
        if(selectedClassroomEntity == null) throw new NotFoundException("Classroom not found with id: " + classroomId);

        SchoolEntity schoolEntity = this.schoolRepository.findById(schoolId).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School not found with id: " + schoolId);

        selectedClassroomEntity.setName(classroomRequestDTO.getName());
        selectedClassroomEntity.setSchoolEntity(schoolEntity);
        selectedClassroomEntity.setUpdatedAt(LocalDateTime.now());

        this.classroomRepository.save(selectedClassroomEntity);

        return Mapper.mapClassroomToClassroomDTO(selectedClassroomEntity);
    }

    @Transactional
    public void deleteClassroom(long classroomId) throws NotFoundException{
        ClassroomEntity deletedClassroomEntity = this.classroomRepository.findById(classroomId).orElse(null);
        if(deletedClassroomEntity == null) throw new NotFoundException("Classroom not found with id: " + classroomId);
        this.classroomRepository.delete(deletedClassroomEntity);
    }

    public long getAllClassroomsCount(long schoolId) {
        return this.classroomRepository.findAllCountBySchoolId(schoolId);
    }
}
