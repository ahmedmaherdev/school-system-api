package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponse;
import com.ahmedmaher.schoolsystem.dto.SchoolDTO;
import com.ahmedmaher.schoolsystem.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<SchoolDTO>>> getAllSchools() {
        List<SchoolDTO> schools = this.schoolService.getAllSchools();
        long allCount = this.schoolService.getAllSchoolsCount();
        int count = schools.size();
        CustomResponse<List<SchoolDTO>> customResponse = new CustomResponse<>(schools , count, allCount);
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolDTO> getUser(@PathVariable("schoolId") Long schoolId){
        SchoolDTO school = this.schoolService.getSchoolById(schoolId);
        return ResponseEntity.ok(school);
    }

    @PostMapping("/")
    public ResponseEntity<SchoolDTO> createUser(@Valid @RequestBody SchoolDTO schoolDTO) {
        SchoolDTO createdSchool = this.schoolService.createSchool(schoolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchool);
    }

    @PutMapping("/{schoolId}")
    public ResponseEntity<SchoolDTO> updateUser(@Valid @RequestBody() SchoolDTO schoolDTO , @PathVariable("schoolId") long schoolId) {
        SchoolDTO updatedSchool = this.schoolService.updateSchool(schoolId , schoolDTO);
        return ResponseEntity.ok(updatedSchool);
    }

    @DeleteMapping("/{schoolId}")
    public ResponseEntity<?> deleteUser( @PathVariable("schoolId") long schoolId) {
       this.schoolService.deleteSchool(schoolId);
        return ResponseEntity.noContent().build();
    }
}
