package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolRequestDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import com.ahmedmaher.schoolsystem.service.school.SchoolService;
import com.ahmedmaher.schoolsystem.config.EndpointConfig;
import com.ahmedmaher.schoolsystem.util.AppFeatures;
import com.ahmedmaher.schoolsystem.util.mapper.SchoolMapper;
import com.ahmedmaher.schoolsystem.util.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(EndpointConfig.SCHOOL)
public class SchoolController {
    private final SchoolService schoolService;
    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public ResponseEntity<CustomResponseDTO<?>> getAllSchools(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-createdAt") String sort
    ) {
        AppFeatures appFeatures = new AppFeatures(sort , size , page);
        List<SchoolEntity> schoolEntities = this.schoolService.getAll(appFeatures.splitPageable());
        List<SchoolResponseDTO> schools = SchoolMapper.mapSchoolEntitiesToSchoolResponseDTOs(schoolEntities);
        long allCount = this.schoolService.getAllSchoolsCount();
        int count = schools.size();

        // handle response
        Map<String , Object> res = new HashMap<>();
        res.put("schools" , schools);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolResponseDTO> getSchool(@PathVariable("schoolId") Long schoolId){
        SchoolEntity schoolEntity = this.schoolService.getOne(schoolId);
        return ResponseEntity.ok(
                SchoolMapper.mapSchoolEntityToSchoolResponseDTO(schoolEntity)
        );
    }

    @PostMapping
    public ResponseEntity<SchoolResponseDTO> createSchool(@Valid @RequestBody SchoolRequestDTO schoolRequestDTO) {
        SchoolEntity schoolEntity = SchoolMapper.mapSchoolRequestToSchoolEntity(schoolRequestDTO);
        SchoolEntity createdSchool = this.schoolService.createOne(schoolEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SchoolMapper.mapSchoolEntityToSchoolResponseDTO(createdSchool)
        );
    }


    @PutMapping("/{schoolId}")
    public ResponseEntity<SchoolResponseDTO> updateSchool(
            @Valid @RequestBody() SchoolRequestDTO schoolRequestDTO,
            @PathVariable("schoolId") long schoolId
    ) {
        SchoolEntity schoolEntity = SchoolMapper.mapSchoolRequestToSchoolEntity(schoolRequestDTO);
        SchoolEntity updatedSchool = this.schoolService.updateOne(
                schoolId,
                schoolEntity
        );
        return ResponseEntity.ok(
                SchoolMapper.mapSchoolEntityToSchoolResponseDTO(updatedSchool)
        );
    }
    @DeleteMapping("/{schoolId}")
    public ResponseEntity<?> deleteSchool( @PathVariable("schoolId") long schoolId) {
       this.schoolService.deleteOne(schoolId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SchoolResponseDTO>> searchSchool(
            @RequestParam String s
    ) {
        Pageable pageable = PageRequest.of(0 , 10);
        return ResponseEntity.ok(
                SchoolMapper.mapSchoolEntitiesToSchoolResponseDTOs(
                        this.schoolService.search(s , pageable)
                )
        );
    }
}
