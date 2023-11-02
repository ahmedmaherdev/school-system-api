package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.ClassroomRequestDTO;
import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.service.ClassroomService;
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
@RequestMapping("/api/schools/{schoolId}/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;
    private final SchoolService schoolService;


    @Autowired
    public ClassroomController(ClassroomService classroomService , SchoolService schoolService) {
        this.classroomService = classroomService;
        this.schoolService = schoolService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponseDTO<?>> getAllClassrooms(
            @PathVariable("schoolId") long schoolId ,
            @RequestParam(defaultValue = "0" ) int page ,
            @RequestParam(defaultValue = "10" ) int size,
            @RequestParam(defaultValue = "createdAt") String sort
    ) {
        AppFeatures appFeatures = new AppFeatures(sort , size , page);
        List<ClassroomResponseDTO> classrooms = this.classroomService.getAllClassrooms(schoolId , appFeatures.splitPageable());
        long allCount = this.classroomService.getAllClassroomsCount(schoolId);
        int count = classrooms.size();
        Map<String , Object> res = new HashMap<>();
        res.put("classrooms" , classrooms);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomResponseDTO> getClassroom(@PathVariable("classroomId") Long classroomId){
        ClassroomResponseDTO classroom = this.classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(classroom);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @PostMapping("/")
    public ResponseEntity<ClassroomResponseDTO> createClassroom(
            @PathVariable("schoolId") long schoolId,
            @Valid @RequestBody ClassroomRequestDTO classroomRequestDTO
    ) {
        SchoolResponseDTO schoolResponseDTO = this.schoolService.getSchoolById(schoolId);
        ClassroomResponseDTO createdClassroom = this.classroomService.createClassroom(schoolId ,  classroomRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClassroom);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @PutMapping("/{classroomId}")
    public ResponseEntity<ClassroomResponseDTO> updateClassroom(
            @Valid @RequestBody() ClassroomRequestDTO classroomRequestDTO,
            @PathVariable("classroomId") long classroomId,
            @PathVariable("schoolId") long schoolId
    ) {
        ClassroomResponseDTO updatedClassroom = this.classroomService.updateClassroom(classroomId , schoolId, classroomRequestDTO);
        return ResponseEntity.ok(updatedClassroom);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @DeleteMapping("/{classroomId}")
    public ResponseEntity<?> deleteClassroom( @PathVariable("classroomId") long classroomId) {
        this.classroomService.deleteClassroom(classroomId);
        return ResponseEntity.noContent().build();
    }
}
