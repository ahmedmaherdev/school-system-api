package com.ahmedmaher.schoolsystem.service.school;

import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchoolServiceImp implements SchoolService {
    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolServiceImp(SchoolRepository schoolRepository){
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<SchoolEntity> getAll(Pageable pageable) {
        Page<SchoolEntity> schools = this.schoolRepository.findAll(pageable);
        return schools.getContent();
    }

    @Override
    public SchoolEntity getOne(long id) throws NotFoundException {
        SchoolEntity schoolEntity = this.schoolRepository.findById(id).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School not found with id: " + id);
        return schoolEntity;
    }

    @Transactional
    @Override
    public SchoolEntity createOne(SchoolEntity entity) {
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        this.schoolRepository.save(entity);
        return entity;
    }

    @Transactional
    @Override
    public SchoolEntity updateOne(long id, SchoolEntity entity) throws NotFoundException {
        SchoolEntity schoolEntity = this.schoolRepository.findById(id).orElse(null);
        if(schoolEntity == null)
            throw new NotFoundException("School not found with id: " + id);
        entity.setId(id);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(schoolEntity.getCreatedAt());
        this.schoolRepository.save(entity);
        return entity;
    }

    @Transactional
    @Override
    public void deleteOne(long id) throws NotFoundException {
        SchoolEntity deletedSchoolEntity = this.schoolRepository.findById(id).orElse(null);
        if(deletedSchoolEntity == null)
            throw new NotFoundException("School not found with id: " + id);
        this.schoolRepository.delete(deletedSchoolEntity);
    }

    @Override
    public List<SchoolEntity> search(String word , Pageable pageable) {
        return this.schoolRepository.searchBy(word, pageable);
    }
    @Override
    public long getAllSchoolsCount() {
        return this.schoolRepository.count();
    }
}
