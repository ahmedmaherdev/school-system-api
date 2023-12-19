package com.ahmedmaher.schoolsystem.service.classroom;

import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
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
    public List<ClassroomEntity> getAll(Pageable pageable) {
        Page<ClassroomEntity> classrooms = this.classroomRepository.findAll(pageable);
        return classrooms.getContent();
    }

    @Override
    public List<ClassroomEntity> getAllBySchoolId(long schoolId, Pageable pageable) {
        Page<ClassroomEntity> classrooms = this.classroomRepository.findAllClassroomsBySchoolId(schoolId , pageable);
        return classrooms.getContent();
    }

    @Override
    public List<UserEntity> getAllStudentsByClassroomId(long classroomId) {
        ClassroomEntity classroomEntity = classroomRepository.findClassroomWithAllStudents(classroomId);
        return classroomEntity.getUsers();
    }

    @Override
    public ClassroomEntity getOne(long id) throws NotFoundException {
        ClassroomEntity classroomEntity = this.classroomRepository.findById(id).orElse(null);
        if(classroomEntity == null) throw new NotFoundException("Classroom not found with id: " + id);
        return classroomEntity;
    }

    @Transactional
    @Override
    public ClassroomEntity createOne(ClassroomEntity entity) {
        long schoolId = entity.getSchool().getId();
        SchoolEntity schoolEntity = this.schoolRepository.findById(schoolId).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School not found with id: " + schoolId);

        entity.setCapacity(0);
        entity.setSchool(schoolEntity);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        this.classroomRepository.save(entity);
        return entity;
    }

    @Override
    public ClassroomEntity updateOne(long id, ClassroomEntity entity) throws NotFoundException {
        ClassroomEntity selectedClassroomEntity = this.classroomRepository.findById(id).orElse(null);
        if(selectedClassroomEntity == null) throw new NotFoundException("Classroom not found with id: " + id);

        long schoolId = selectedClassroomEntity.getSchool().getId();
        if(!this.schoolRepository.existsById(schoolId))
            throw new NotFoundException("School not found with id: " + schoolId);

        entity.setId(id);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(selectedClassroomEntity.getCreatedAt());
        this.classroomRepository.save(entity);
        return entity;
    }

    @Override
    public void deleteOne(long id) throws NotFoundException {
        ClassroomEntity deletedClassroomEntity = this.classroomRepository.findById(id).orElse(null);
        if(deletedClassroomEntity == null) throw new NotFoundException("Classroom not found with id: " + id);
        deletedClassroomEntity.setSchool(null);
        this.classroomRepository.delete(deletedClassroomEntity);
    }

    @Override
    public List<ClassroomEntity> search(String word , Pageable pageable) {
        return this.classroomRepository.searchBy(word, pageable);
    }

    @Override
    public long getAllClassroomsCount(long schoolId) {
        return this.classroomRepository.findAllCountBySchoolId(schoolId);
    }
}
