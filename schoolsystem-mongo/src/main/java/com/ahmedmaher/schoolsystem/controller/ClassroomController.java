package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomReqDTO;
import com.ahmedmaher.schoolsystem.dto.CustomResDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResDTO;
import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.service.classroom.ClassroomService;
import com.ahmedmaher.schoolsystem.util.AppFeaturesUtil;
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
    public ResponseEntity<CustomResDTO<?>> getAllClassrooms(
            @PathVariable("schoolId") String schoolId ,
            @RequestParam(defaultValue = "0" ) int page ,
            @RequestParam(defaultValue = "10" ) int size,
            @RequestParam(defaultValue = "createdAt") String sort
    ) {
        AppFeaturesUtil appFeaturesUtil = new AppFeaturesUtil(sort , size , page);
        List<ClassroomDoc> classroomEntities = this.classroomService.getAllBySchoolId(
                schoolId ,
                appFeaturesUtil.splitPageable()
        );
        List<ClassroomResDTO> classrooms = ClassroomMapper.mapToClassroomResponseDTOs(classroomEntities);
        long allCount = this.classroomService.getAllClassroomsCount(schoolId);
        int count = classrooms.size();

        // handle response
        Map<String , Object> res = new HashMap<>();
        res.put("classrooms" , classrooms);
        CustomResDTO<Map<String , Object>> customResDTO = new CustomResDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResDTO);
    }

    @GetMapping("${app.config.backend.classroom.api.load-classroom-by-id-uri}")
    public ResponseEntity<ClassroomResDTO> getClassroom(@PathVariable("classroomId") String classroomId){
        ClassroomDoc classroom = this.classroomService.getOne(classroomId);
        return ResponseEntity.ok(
                ClassroomMapper.mapToClassroomResponseDTO(classroom)
        );
    }

    @RolesAllowed({UserRole.Names.ADMIN , UserRole.Names.SUPERADMIN})
    @PostMapping("${app.config.backend.classroom.api.create-classroom-uri}")
    public ResponseEntity<ClassroomResDTO> createClassroom(
            @PathVariable("schoolId") String schoolId,
            @Valid @RequestBody ClassroomReqDTO classroomReqDTO
    ) {
        classroomReqDTO.setSchoolId(schoolId);
        ClassroomDoc classroomEntity = ClassroomMapper.mapToClassroomDocument(classroomReqDTO);
        this.classroomService.createOne(classroomEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ClassroomMapper.mapToClassroomResponseDTO(classroomEntity)
        );
    }
    @RolesAllowed({UserRole.Names.ADMIN , UserRole.Names.SUPERADMIN})
    @PutMapping("${app.config.backend.classroom.api.load-classroom-by-id-uri}")
    public ResponseEntity<ClassroomResDTO> updateClassroom(
            @Valid @RequestBody() ClassroomReqDTO classroomReqDTO,
            @PathVariable("classroomId") String classroomId,
            @PathVariable("schoolId") String schoolId
    ) {
        classroomReqDTO.setSchoolId(schoolId);
        ClassroomDoc classroomEntity = ClassroomMapper.mapToClassroomDocument(classroomReqDTO);
        this.classroomService.updateOne(
                classroomId ,
               classroomEntity
        );
        return ResponseEntity.ok(
                ClassroomMapper.mapToClassroomResponseDTO(classroomEntity)
        );
    }

    @GetMapping("${app.config.backend.classroom.api.load-search-classrooms-uri}")
    public ResponseEntity<List<ClassroomResDTO>> searchSchool(
            @RequestParam String s
    ) {
        Pageable pageable = PageRequest.of(0 , 10);
        return ResponseEntity.ok(
                ClassroomMapper.mapToClassroomResponseDTOs(
                        this.classroomService.search(s , pageable)
                )
        );
    }

    @RolesAllowed({UserRole.Names.ADMIN , UserRole.Names.SUPERADMIN})
    @DeleteMapping("${app.config.backend.classroom.api.load-classroom-by-id-uri}")
    public ResponseEntity<?> deleteClassroom( @PathVariable("classroomId") String classroomId) {
        this.classroomService.deleteOne(classroomId);
        return ResponseEntity.noContent().build();
    }
}
