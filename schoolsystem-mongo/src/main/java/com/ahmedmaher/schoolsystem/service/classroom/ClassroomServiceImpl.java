package com.ahmedmaher.schoolsystem.service.classroom;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.SchoolDocument;
import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ClassroomServiceImpl implements ClassroomService{
    private final ClassroomRepository classroomRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository classroomRepository , SchoolRepository schoolRepository){
        this.classroomRepository = classroomRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<ClassroomDocument> getAll(Pageable pageable) {
        Page<ClassroomDocument> classrooms = this.classroomRepository.findAll(pageable);
        return classrooms.getContent();
    }

    @Override
    public List<ClassroomDocument> getAllBySchoolId(String schoolId, Pageable pageable) {
        return this.classroomRepository.findBySchool(schoolId , pageable);
    }

    @Override
    public ClassroomDocument getOne(String id) throws NotFoundException {
        ClassroomDocument classroomEntity = this.classroomRepository.findById(id).orElse(null);
        if(classroomEntity == null) throw new NotFoundException("Classroom not found with id: " + id);
        return classroomEntity;
    }

    @Transactional
    @Override
    public ClassroomDocument createOne(ClassroomDocument document) {
        String schoolId = document.getSchool().getId();
        SchoolDocument schoolEntity = this.schoolRepository.findById(schoolId).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School not found with id: " + schoolId);

        document.setCapacity(0);
        document.setSchool(schoolEntity);
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        this.classroomRepository.save(document);
        return document;
    }

    @Override
    public ClassroomDocument updateOne(String id, ClassroomDocument entity) throws NotFoundException {
        ClassroomDocument selectedClassroomEntity = this.classroomRepository.findById(id).orElse(null);
        if(selectedClassroomEntity == null) throw new NotFoundException("Classroom not found with id: " + id);

        String schoolId = selectedClassroomEntity.getSchool().getId();
        if(!this.schoolRepository.existsById(schoolId))
            throw new NotFoundException("School not found with id: " + schoolId);

        entity.setId(id);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(selectedClassroomEntity.getCreatedAt());
        this.classroomRepository.save(entity);
        return entity;
    }

    @Override
    public void deleteOne(String id) throws NotFoundException {
        ClassroomDocument deletedClassroomEntity = this.classroomRepository.findById(id).orElse(null);
        if(deletedClassroomEntity == null) throw new NotFoundException("Classroom not found with id: " + id);
        deletedClassroomEntity.setSchool(null);
        this.classroomRepository.delete(deletedClassroomEntity);
    }

    @Override
    public List<ClassroomDocument> search(String word , Pageable pageable) {
        return this.classroomRepository.searchByName(word, pageable);
    }

    @Override
    public long getAllClassroomsCount(String schoolId) {
        return this.classroomRepository.findCountBySchool(schoolId);
    }
}
