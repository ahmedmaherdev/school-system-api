package com.ahmedmaher.schoolsystem.service.school;

import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import com.ahmedmaher.schoolsystem.document.SchoolDoc;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepo;
import com.ahmedmaher.schoolsystem.repository.SchoolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchoolServiceImp implements SchoolService {
    private final SchoolRepo schoolRepo;
    private final ClassroomRepo classroomRepo;


    @Autowired
    public SchoolServiceImp(SchoolRepo schoolRepo, ClassroomRepo classroomRepo) {
        this.schoolRepo = schoolRepo;
        this.classroomRepo = classroomRepo;
    }

    @Override
    public List<SchoolDoc> getAll(Pageable pageable) {
        Page<SchoolDoc> schools = schoolRepo.findAll(pageable);
        return schools.getContent();
    }

    @Override
    public SchoolDoc getOne(String id) throws NotFoundException {
        SchoolDoc schoolEntity = schoolRepo.findById(id).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School not found with id: " + id);
        return schoolEntity;
    }

    @Transactional
    @Override
    public SchoolDoc createOne(SchoolDoc document) {
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        schoolRepo.save(document);
        return document;
    }

    @Transactional
    @Override
    public SchoolDoc updateOne(String id, SchoolDoc entity) throws NotFoundException {
        SchoolDoc schoolEntity = schoolRepo.findById(id).orElse(null);
        if(schoolEntity == null)
            throw new NotFoundException("School not found with id: " + id);
        entity.setId(id);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(schoolEntity.getCreatedAt());
        schoolRepo.save(entity);
        return entity;
    }

    @Transactional
    @Override
    public void deleteOne(String id) throws NotFoundException {
        SchoolDoc deletedSchoolEntity = getOne(id);
        schoolRepo.delete(deletedSchoolEntity);
    }

    @Override
    public List<SchoolDoc> search(String word , Pageable pageable) {
        return schoolRepo.searchByName(word, pageable);
    }
    @Override
    public long getAllSchoolsCount() {
        return schoolRepo.count();
    }

    @Override
    public List<ClassroomDoc> getSchoolClassrooms(String schoolId , Pageable pageable) {
        SchoolDoc school = getOne(schoolId);
        return classroomRepo.findBySchool(school.getId() , pageable);
    }
}
