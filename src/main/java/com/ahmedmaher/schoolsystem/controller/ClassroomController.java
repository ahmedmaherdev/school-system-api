package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomRequestDTO;
import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.service.classroom.ClassroomService;
import com.ahmedmaher.schoolsystem.util.AppFeatures;
import com.ahmedmaher.schoolsystem.util.mapper.ClassroomMapper;
import jakarta.annotation.security.RolesAllowed;
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
@RequestMapping("${app.config.backend.classroom.base-uri}")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping("${app.config.backend.classroom.api.load-all-classrooms-uri}")
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

    @GetMapping("${app.config.backend.classroom.api.load-classroom-by-id-uri}")
    public ResponseEntity<ClassroomResponseDTO> getClassroom(@PathVariable("classroomId") Long classroomId){
        ClassroomEntity classroom = this.classroomService.getOne(classroomId);
        return ResponseEntity.ok(
                ClassroomMapper.mapClassroomEntityToClassroomResponseDTO(classroom)
        );
    }

    @RolesAllowed({UserRole.Names.ADMIN , UserRole.Names.SUPERADMIN})
    @PostMapping("${app.config.backend.classroom.api.create-classroom-uri}")
    public ResponseEntity<ClassroomResponseDTO> createClassroom(
            @PathVariable("schoolId") long schoolId,
            @Valid @RequestBody ClassroomRequestDTO classroomRequestDTO
    ) {
        classroomRequestDTO.setSchoolId(schoolId);
        ClassroomEntity classroomEntity = ClassroomMapper.mapClassroomRequestToClassroomEntity(classroomRequestDTO);
        this.classroomService.createOne(classroomEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClassroomMapper.mapClassroomEntityToClassroomResponseDTO(classroomEntity)
        );
    }
    @RolesAllowed({UserRole.Names.ADMIN , UserRole.Names.SUPERADMIN})
    @PutMapping("${app.config.backend.classroom.api.load-classroom-by-id-uri}")
    public ResponseEntity<ClassroomResponseDTO> updateClassroom(
            @Valid @RequestBody() ClassroomRequestDTO classroomRequestDTO,
            @PathVariable("classroomId") long classroomId,
            @PathVariable("schoolId") long schoolId
    ) {
        classroomRequestDTO.setSchoolId(schoolId);
        ClassroomEntity classroomEntity = ClassroomMapper.mapClassroomRequestToClassroomEntity(classroomRequestDTO);
        this.classroomService.updateOne(
                classroomId ,
               classroomEntity
        );
        return ResponseEntity.ok(
                ClassroomMapper.mapClassroomEntityToClassroomResponseDTO(classroomEntity)
        );
    }

    @GetMapping("${app.config.backend.classroom.api.load-search-classrooms-uri}")
    public ResponseEntity<List<ClassroomResponseDTO>> searchSchool(
            @RequestParam String s
    ) {
        Pageable pageable = PageRequest.of(0 , 10);
        return ResponseEntity.ok(
                ClassroomMapper.mapClassroomEntitiesToClassroomResponseDTOs(
                        this.classroomService.search(s , pageable)
                )
        );
    }

    @RolesAllowed({UserRole.Names.ADMIN , UserRole.Names.SUPERADMIN})
    @DeleteMapping("${app.config.backend.classroom.api.load-classroom-by-id-uri}")
    public ResponseEntity<?> deleteClassroom( @PathVariable("classroomId") long classroomId) {
        this.classroomService.deleteOne(classroomId);
        return ResponseEntity.noContent().build();
    }
}
