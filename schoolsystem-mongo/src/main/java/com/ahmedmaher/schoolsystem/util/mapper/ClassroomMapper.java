package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomReqDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResDTO;
import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import com.ahmedmaher.schoolsystem.document.SchoolDoc;

import java.util.List;
import java.util.stream.Collectors;

public class ClassroomMapper {

    public static ClassroomResDTO mapToClassroomResponseDTO(ClassroomDoc classroomDoc){

        return new ClassroomResDTO(
                classroomDoc.getId(),
                classroomDoc.getName(),
                classroomDoc.getCapacity(),
                SchoolMapper.mapToSchoolResponseDTO(classroomDoc.getSchool()),
                classroomDoc.getCreatedAt(),
                classroomDoc.getUpdatedAt()
        );
    }

    public static ClassroomDoc mapToClassroomDocument(ClassroomReqDTO classroomReqDTO){
        return ClassroomDoc.builder()
                .name(classroomReqDTO.getName())
                .school(SchoolDoc.builder().id(classroomReqDTO.getSchoolId()).build())
                .build();
    }

    public static List<ClassroomResDTO> mapToClassroomResponseDTOs(List<ClassroomDoc> classroomDocs){
        return classroomDocs.stream().map(ClassroomMapper::mapToClassroomResponseDTO).collect(Collectors.toList());
    }

}
