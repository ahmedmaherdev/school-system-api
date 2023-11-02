package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.SchoolRequestDTO;
import com.ahmedmaher.schoolsystem.dto.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
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
public class SchoolService {
    private SchoolRepository schoolRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository){
        this.schoolRepository = schoolRepository;
    }
    public List<SchoolResponseDTO> getAllSchools(Pageable pageable) {
        Page<SchoolEntity> schools = this.schoolRepository.findAll(pageable);
        List<SchoolResponseDTO> schoolResponseDTOS = schools.getContent().stream().map(Mapper::mapSchoolToSchoolDTO).collect(Collectors.toList());
        return schoolResponseDTOS;
    }

    public SchoolResponseDTO getSchoolById(Long schoolId) throws NotFoundException {
        SchoolEntity schoolEntity = this.schoolRepository.findById(schoolId).orElse(null);
        if(schoolEntity == null) throw new NotFoundException("School not found with id: " + schoolId);
        return Mapper.mapSchoolToSchoolDTO(schoolEntity);
    }

    @Transactional
    public SchoolResponseDTO createSchool(SchoolRequestDTO schoolRequestDTO){
        SchoolEntity schoolEntity =  new SchoolEntity();
        schoolEntity.setName(schoolRequestDTO.getName());
        schoolEntity.setAddress(schoolRequestDTO.getAddress());
        schoolEntity.setCreatedAt(LocalDateTime.now());
        schoolEntity.setUpdatedAt(LocalDateTime.now());

        this.schoolRepository.save(schoolEntity);
        return Mapper.mapSchoolToSchoolDTO(schoolEntity);
    }

    @Transactional
    public SchoolResponseDTO updateSchool(long schoolId, SchoolRequestDTO schoolRequestDTO) throws NotFoundException{
        SchoolEntity selectedSchoolEntity = this.schoolRepository.findById(schoolId).orElse(null);
        if(selectedSchoolEntity == null) throw new NotFoundException("School not found with id: " + schoolId);

        selectedSchoolEntity.setName(schoolRequestDTO.getName());
        selectedSchoolEntity.setAddress(schoolRequestDTO.getAddress());
        selectedSchoolEntity.setUpdatedAt(LocalDateTime.now());

        return Mapper.mapSchoolToSchoolDTO(selectedSchoolEntity);
    }

    @Transactional
    public boolean deleteSchool(long schoolId) throws NotFoundException{
        SchoolEntity deletedSchoolEntity = this.schoolRepository.findById(schoolId).orElse(null);
        if(deletedSchoolEntity == null) throw new NotFoundException("School not found with id: " + schoolId);
        this.schoolRepository.delete(deletedSchoolEntity);
        return true;
    }

    public long getAllSchoolsCount() {
        return this.schoolRepository.count();
    }


}
