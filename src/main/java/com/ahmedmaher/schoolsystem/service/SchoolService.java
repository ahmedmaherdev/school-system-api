package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.SchoolDTO;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.model.School;
import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {
    private SchoolRepository schoolRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository){
        this.schoolRepository = schoolRepository;
    }
    public List<SchoolDTO> getAllSchools() {
        List<School> schools = this.schoolRepository.findAll();
        List<SchoolDTO> schoolDTOs =  schools.stream().map(Mapper::mapSchoolToSchoolDTO).collect(Collectors.toList());
        return schoolDTOs;
    }

    public SchoolDTO getSchoolById(Long schoolId) throws NotFoundException {
        School school = this.schoolRepository.findById(schoolId).orElse(null);
        if(school == null) throw new NotFoundException("School not found with id: " + schoolId);
        return Mapper.mapSchoolToSchoolDTO(school);
    }

    @Transactional
    public SchoolDTO createSchool(SchoolDTO schoolDTO){
        School school =  new School();
        school.setName(schoolDTO.getName());
        school.setAddress(schoolDTO.getAddress());
        school.setCreatedAt(LocalDateTime.now());
        school.setUpdatedAt(LocalDateTime.now());

        this.schoolRepository.save(school);
        return Mapper.mapSchoolToSchoolDTO(school);
    }

    @Transactional
    public SchoolDTO updateSchool(long schoolId, SchoolDTO schoolDTO) throws NotFoundException{
        School selectedSchool = this.schoolRepository.findById(schoolId).orElse(null);
        if(selectedSchool == null) throw new NotFoundException("School not found with id: " + schoolId);

        selectedSchool.setName(schoolDTO.getName());
        selectedSchool.setAddress(schoolDTO.getAddress());
        selectedSchool.setUpdatedAt(LocalDateTime.now());

        return Mapper.mapSchoolToSchoolDTO(selectedSchool);
    }

    @Transactional
    public boolean deleteSchool(long schoolId) throws NotFoundException{
        School deletedSchool = this.schoolRepository.findById(schoolId).orElse(null);
        if(deletedSchool == null) throw new NotFoundException("School not found with id: " + schoolId);
        this.schoolRepository.delete(deletedSchool);
        return true;
    }

    public long getAllSchoolsCount() {
        return this.schoolRepository.count();
    }


}
