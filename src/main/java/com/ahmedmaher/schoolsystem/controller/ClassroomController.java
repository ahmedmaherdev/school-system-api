package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.ClassroomDTO;
import com.ahmedmaher.schoolsystem.dto.SchoolDTO;
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

    private  ClassroomService classroomService;
    private SchoolService schoolService;


    @Autowired
    public ClassroomController(ClassroomService classroomService , SchoolService schoolService) {
        this.classroomService = classroomService;
        this.schoolService = schoolService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponseDTO<?>> getAllClassrooms(@PathVariable("schoolId") long schoolId ,
                                                                 @RequestParam(defaultValue = "0" ) int page ,
                                                                 @RequestParam(defaultValue = "10" ) int size,
                                                                 @RequestParam(defaultValue = "createdAt") String sort
    ) {
        AppFeatures appFeatures = new AppFeatures(sort , size , page);
        List<ClassroomDTO> classrooms = this.classroomService.getAllClassrooms(schoolId , appFeatures.splitPageable());
        long allCount = this.classroomService.getAllClassroomsCount(schoolId);
        int count = classrooms.size();
        Map<String , Object> res = new HashMap<>();
        res.put("classrooms" , classrooms);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomDTO> getClassroom(@PathVariable("classroomId") Long classroomId){
        ClassroomDTO classroom = this.classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(classroom);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @PostMapping("/")
    public ResponseEntity<ClassroomDTO> createClassroom(@PathVariable("schoolId") long schoolId, @Valid @RequestBody ClassroomDTO classroomDTO) {
        SchoolDTO schoolDTO = this.schoolService.getSchoolById(schoolId);
        classroomDTO.setSchoolId(schoolDTO.getId());
        ClassroomDTO createdClassroom = this.classroomService.createClassroom(classroomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClassroom);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @PutMapping("/{classroomId}")
    public ResponseEntity<ClassroomDTO> updateUser(@Valid @RequestBody() ClassroomDTO classroomDTO ,
                                                   @PathVariable("classroomId") long classroomId,
                                                   @PathVariable("schoolId") long schoolId) {
        SchoolDTO schoolDTO = this.schoolService.getSchoolById(schoolId);
        classroomDTO.setSchoolId(schoolDTO.getId());
        ClassroomDTO updatedClassroom = this.classroomService.updateClassroom(classroomId , classroomDTO);
        return ResponseEntity.ok(updatedClassroom);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @DeleteMapping("/{classroomId}")
    public ResponseEntity<?> deleteUser( @PathVariable("classroomId") long classroomId) {
        this.classroomService.deleteClassroom(classroomId);
        return ResponseEntity.noContent().build();
    }
}
