package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolReqDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolResDTO;
import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import com.ahmedmaher.schoolsystem.document.SchoolDoc;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.service.school.SchoolService;
import com.ahmedmaher.schoolsystem.util.AppFeaturesUtil;
import com.ahmedmaher.schoolsystem.util.mapper.ClassroomMapper;
import com.ahmedmaher.schoolsystem.util.mapper.SchoolMapper;
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
@RequestMapping("${app.config.backend.school.base-uri}")
public class SchoolController {
    private final SchoolService schoolService;
    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("${app.config.backend.school.api.load-all-schools-uri}")
    public ResponseEntity<CustomResDTO<?>> getAllSchools(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-createdAt") String sort
    ) {
        AppFeaturesUtil appFeaturesUtil = new AppFeaturesUtil(sort , size , page);
        List<SchoolDoc> schoolEntities = this.schoolService.getAll(appFeaturesUtil.splitPageable());
        List<SchoolResDTO> schools = SchoolMapper.mapToSchoolResponseDTOs(schoolEntities);
        long allCount = this.schoolService.getAllSchoolsCount();
        int count = schools.size();

        // handle response
        Map<String , Object> res = new HashMap<>();
        res.put("schools" , schools);
        CustomResDTO<Map<String , Object>> customResDTO = new CustomResDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResDTO);
    }

    @GetMapping("${app.config.backend.school.api.load-school-by-id-uri}")
    public ResponseEntity<SchoolResDTO> getSchool(@PathVariable("schoolId") String schoolId){
        SchoolDoc schoolEntity = this.schoolService.getOne(schoolId);
        return ResponseEntity.ok(
                SchoolMapper.mapToSchoolResponseDTO(schoolEntity)
        );
    }

    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @PostMapping("${app.config.backend.school.api.create-school-uri}")
    public ResponseEntity<SchoolResDTO> createSchool(@Valid @RequestBody SchoolReqDTO schoolReqDTO) {
        SchoolDoc schoolEntity = SchoolMapper.mapToSchoolDocument(schoolReqDTO);
        this.schoolService.createOne(schoolEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SchoolMapper.mapToSchoolResponseDTO(schoolEntity)
        );
    }

    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @PutMapping("${app.config.backend.school.api.load-school-by-id-uri}")
    public ResponseEntity<SchoolResDTO> updateSchool(
            @Valid @RequestBody() SchoolReqDTO schoolReqDTO,
            @PathVariable("schoolId") String schoolId
    ) {
        SchoolDoc schoolEntity = SchoolMapper.mapToSchoolDocument(schoolReqDTO);
        this.schoolService.updateOne(
                schoolId,
                schoolEntity
        );
        return ResponseEntity.ok(
                SchoolMapper.mapToSchoolResponseDTO(schoolEntity)
        );
    }
    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @DeleteMapping("${app.config.backend.school.api.load-school-by-id-uri}")
    public ResponseEntity<?> deleteSchool( @PathVariable("schoolId") String schoolId) {
       this.schoolService.deleteOne(schoolId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("${app.config.backend.school.api.load-search-schools-uri}")
    public ResponseEntity<List<SchoolResDTO>> searchSchool(
            @RequestParam String s
    ) {
        Pageable pageable = PageRequest.of(0 , 10);
        return ResponseEntity.ok(
                SchoolMapper.mapToSchoolResponseDTOs(
                        this.schoolService.search(s , pageable)
                )
        );
    }

    @GetMapping("${app.config.backend.school.api.load-all-classrooms-by-school-uri}")
    public ResponseEntity<?> getAllSchoolClassrooms(
            @PathVariable("schoolId") String schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "-createdAt") String sort
    ){
        AppFeaturesUtil appFeaturesUtil = new AppFeaturesUtil(sort , size , page);
        List<ClassroomDoc> classrooms = schoolService.getSchoolClassrooms(schoolId , appFeaturesUtil.splitPageable());
        return ResponseEntity.ok(ClassroomMapper.mapToClassroomResponseDTOs(classrooms));
    }
}
