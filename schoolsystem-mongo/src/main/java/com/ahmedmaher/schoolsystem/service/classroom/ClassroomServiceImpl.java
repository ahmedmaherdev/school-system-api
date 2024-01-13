package com.ahmedmaher.schoolsystem.service.classroom;

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
public class ClassroomServiceImpl implements ClassroomService{
    private final ClassroomRepo classroomRepo;
    private final SchoolRepo schoolRepo;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepo classroomRepo, SchoolRepo schoolRepo){
        this.classroomRepo = classroomRepo;
        this.schoolRepo = schoolRepo;
    }

    @Override
    public List<ClassroomDoc> getAll(Pageable pageable) {
        Page<ClassroomDoc> classrooms = this.classroomRepo.findAll(pageable);
        return classrooms.getContent();
    }

    @Override
    public List<ClassroomDoc> getAllBySchoolId(String schoolId, Pageable pageable) {
        return this.classroomRepo.findBySchool(schoolId , pageable);
    }

    @Override
    public ClassroomDoc getOne(String id) throws NotFoundException {
        ClassroomDoc classroomDoc = this.classroomRepo.findById(id).orElse(null);
        if(classroomDoc == null) throw new NotFoundException("Classroom not found with id: " + id);
        return classroomDoc;
    }

    @Transactional
    @Override
    public ClassroomDoc createOne(ClassroomDoc document) {
        String schoolId = document.getSchool().getId();
        SchoolDoc schoolDoc = this.schoolRepo.findById(schoolId).orElse(null);
        if(schoolDoc == null) throw new NotFoundException("School not found with id: " + schoolId);

        document.setCapacity(0);
        document.setSchool(schoolDoc);
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        this.classroomRepo.save(document);
        return document;
    }

    @Override
    public ClassroomDoc updateOne(String id, ClassroomDoc document) throws NotFoundException {
        ClassroomDoc selectedClassroomDoc = this.classroomRepo.findById(id).orElse(null);
        if(selectedClassroomDoc == null) throw new NotFoundException("Classroom not found with id: " + id);

        String schoolId = selectedClassroomDoc.getSchool().getId();
        if(!this.schoolRepo.existsById(schoolId))
            throw new NotFoundException("School not found with id: " + schoolId);

        document.setId(id);
        document.setUpdatedAt(LocalDateTime.now());
        document.setCreatedAt(selectedClassroomDoc.getCreatedAt());
        this.classroomRepo.save(document);
        return document;
    }

    @Override
    public void deleteOne(String id) throws NotFoundException {
        ClassroomDoc deletedClassroomDoc = this.classroomRepo.findById(id).orElse(null);
        if(deletedClassroomDoc == null) throw new NotFoundException("Classroom not found with id: " + id);
        deletedClassroomDoc.setSchool(null);
        this.classroomRepo.delete(deletedClassroomDoc);
    }

    @Override
    public List<ClassroomDoc> search(String name , Pageable pageable) {
        return this.classroomRepo.searchByName(name, pageable);
    }

    @Override
    public long getAllClassroomsCount(String schoolId) {
        return this.classroomRepo.findCountBySchool(schoolId);
    }
}
