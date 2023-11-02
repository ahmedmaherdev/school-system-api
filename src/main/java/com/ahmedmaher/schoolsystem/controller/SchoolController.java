package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.SchoolDTO;
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

    private SchoolService schoolService;

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
        List<SchoolDTO> schools = this.schoolService.getAllSchools(appFeatures.splitPageable());
        long allCount = this.schoolService.getAllSchoolsCount();
        int count = schools.size();
        Map<String , Object> res = new HashMap<>();
        res.put("schools" , schools);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolDTO> getUser(@PathVariable("schoolId") Long schoolId){
        SchoolDTO school = this.schoolService.getSchoolById(schoolId);
        return ResponseEntity.ok(school);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/")
    public ResponseEntity<SchoolDTO> createUser(@Valid @RequestBody SchoolDTO schoolDTO) {
        SchoolDTO createdSchool = this.schoolService.createSchool(schoolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchool);
    }


    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PutMapping("/{schoolId}")
    public ResponseEntity<SchoolDTO> updateUser(@Valid @RequestBody() SchoolDTO schoolDTO , @PathVariable("schoolId") long schoolId) {
        SchoolDTO updatedSchool = this.schoolService.updateSchool(schoolId , schoolDTO);
        return ResponseEntity.ok(updatedSchool);
    }
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping("/{schoolId}")
    public ResponseEntity<?> deleteUser( @PathVariable("schoolId") long schoolId) {
       this.schoolService.deleteSchool(schoolId);
        return ResponseEntity.noContent().build();
    }
}
