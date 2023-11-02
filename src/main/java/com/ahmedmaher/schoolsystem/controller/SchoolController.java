package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.SchoolRequestDTO;
import com.ahmedmaher.schoolsystem.dto.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.service.SchoolService;
import com.ahmedmaher.schoolsystem.util.AppFeatures;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponseDTO<?>> getAllSchools(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-createdAt") String sort
    ) {
        AppFeatures appFeatures = new AppFeatures(sort , size , page);
        List<SchoolResponseDTO> schools = this.schoolService.getAllSchools(appFeatures.splitPageable());
        long allCount = this.schoolService.getAllSchoolsCount();
        int count = schools.size();
        Map<String , Object> res = new HashMap<>();
        res.put("schools" , schools);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolResponseDTO> getSchool(@PathVariable("schoolId") Long schoolId){
        SchoolResponseDTO school = this.schoolService.getSchoolById(schoolId);
        return ResponseEntity.ok(school);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/")
    public ResponseEntity<SchoolResponseDTO> createSchool(@Valid @RequestBody SchoolRequestDTO schoolRequestDTO) {
        SchoolResponseDTO createdSchool = this.schoolService.createSchool(schoolRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchool);
    }


    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PutMapping("/{schoolId}")
    public ResponseEntity<SchoolResponseDTO> updateSchool(@Valid @RequestBody() SchoolRequestDTO schoolRequestDTO, @PathVariable("schoolId") long schoolId) {
        SchoolResponseDTO updatedSchool = this.schoolService.updateSchool(schoolId , schoolRequestDTO);
        return ResponseEntity.ok(updatedSchool);
    }
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping("/{schoolId}")
    public ResponseEntity<?> deleteSchool( @PathVariable("schoolId") long schoolId) {
       this.schoolService.deleteSchool(schoolId);
        return ResponseEntity.noContent().build();
    }
}
