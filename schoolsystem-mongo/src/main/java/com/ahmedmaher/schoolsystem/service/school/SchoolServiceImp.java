package com.ahmedmaher.schoolsystem.service.school;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.SchoolDocument;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImp implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final ClassroomRepository classroomRepository;


    @Autowired
    public SchoolServiceImp(SchoolRepository schoolRepository, ClassroomRepository classroomRepository) {
        this.schoolRepository = schoolRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    public List<SchoolDocument> getAll(Pageable pageable) {
        Page<SchoolDocument> schools = schoolRepository.findAll(pageable);
        return schools.getContent();
    }

    @Override
    public SchoolDocument getOne(String id) throws NotFoundException {
        SchoolDocument schoolEntity = schoolRepository.findById(id).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School not found with id: " + id);
        return schoolEntity;
    }

    @Transactional
    @Override
    public SchoolDocument createOne(SchoolDocument document) {
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        schoolRepository.save(document);
        return document;
    }

    @Transactional
    @Override
    public SchoolDocument updateOne(String id, SchoolDocument entity) throws NotFoundException {
        SchoolDocument schoolEntity = schoolRepository.findById(id).orElse(null);
        if(schoolEntity == null)
            throw new NotFoundException("School not found with id: " + id);
        entity.setId(id);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(schoolEntity.getCreatedAt());
        schoolRepository.save(entity);
        return entity;
    }

    @Transactional
    @Override
    public void deleteOne(String id) throws NotFoundException {
        SchoolDocument deletedSchoolEntity = getOne(id);
        schoolRepository.delete(deletedSchoolEntity);
    }

    @Override
    public List<SchoolDocument> search(String word , Pageable pageable) {
        return schoolRepository.searchByName(word, pageable);
    }
    @Override
    public long getAllSchoolsCount() {
        return schoolRepository.count();
    }

    @Override
    public List<ClassroomDocument> getSchoolClassrooms(String schoolId , Pageable pageable) {
        SchoolDocument school = getOne(schoolId);
        return classroomRepository.findBySchool(school.getId() , pageable);
    }
}
