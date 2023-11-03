package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomRequestDTO;
import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.service.classroom.ClassroomService;
import com.ahmedmaher.schoolsystem.service.school.SchoolService;
import com.ahmedmaher.schoolsystem.util.APIRoutes;
import com.ahmedmaher.schoolsystem.util.AppFeatures;
import com.ahmedmaher.schoolsystem.util.mapper.ClassroomMapper;
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
@RequestMapping(APIRoutes.CLASSROOM)
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService , SchoolService schoolService) {
        this.classroomService = classroomService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponseDTO<?>> getAllClassrooms(
            @PathVariable("schoolId") long schoolId ,
            @RequestParam(defaultValue = "0" ) int page ,
            @RequestParam(defaultValue = "10" ) int size,
            @RequestParam(defaultValue = "createdAt") String sort
    ) {
        AppFeatures appFeatures = new AppFeatures(sort , size , page);
        List<ClassroomEntity> classroomEntities = this.classroomService.getAllBySchoolId(
                schoolId ,
                appFeatures.splitPageable()
        );
        List<ClassroomResponseDTO> classrooms = ClassroomMapper.mapClassroomEntitiesToClassroomResponseDTOs(classroomEntities);
        long allCount = this.classroomService.getAllClassroomsCount(schoolId);
        int count = classrooms.size();

        // handle response
        Map<String , Object> res = new HashMap<>();
        res.put("classrooms" , classrooms);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomResponseDTO> getClassroom(@PathVariable("classroomId") Long classroomId){
        ClassroomEntity classroom = this.classroomService.getOne(classroomId);
        return ResponseEntity.ok(
                ClassroomMapper.mapClassroomEntityToClassroomResponseDTO(classroom)
        );
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @PostMapping("/")
    public ResponseEntity<ClassroomResponseDTO> createClassroom(
            @PathVariable("schoolId") long schoolId,
            @Valid @RequestBody ClassroomRequestDTO classroomRequestDTO
    ) {
        classroomRequestDTO.setSchoolId(schoolId);
        ClassroomEntity createdClassroom = this.classroomService.createOne(
            ClassroomMapper.mapClassroomRequestToClassroomEntity(classroomRequestDTO)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClassroomMapper.mapClassroomEntityToClassroomResponseDTO(createdClassroom)
        );
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @PutMapping("/{classroomId}")
    public ResponseEntity<ClassroomResponseDTO> updateClassroom(
            @Valid @RequestBody() ClassroomRequestDTO classroomRequestDTO,
            @PathVariable("classroomId") long classroomId,
            @PathVariable("schoolId") long schoolId
    ) {
        classroomRequestDTO.setSchoolId(schoolId);
        ClassroomEntity updatedClassroom = this.classroomService.updateOne(
                classroomId ,
                ClassroomMapper.mapClassroomRequestToClassroomEntity(classroomRequestDTO)
        );
        return ResponseEntity.ok(
                ClassroomMapper.mapClassroomEntityToClassroomResponseDTO(updatedClassroom)
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @DeleteMapping("/{classroomId}")
    public ResponseEntity<?> deleteClassroom( @PathVariable("classroomId") long classroomId) {
        this.classroomService.deleteOne(classroomId);
        return ResponseEntity.noContent().build();
    }
}
