package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomReqDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResDTO;
import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.SchoolDocument;

import java.util.List;
import java.util.stream.Collectors;

public class ClassroomMapper {

    public static ClassroomResDTO mapToClassroomResponseDTO(ClassroomDocument classroomDocument){

        return new ClassroomResDTO(
                classroomDocument.getId(),
                classroomDocument.getName(),
                classroomDocument.getCapacity(),
                SchoolMapper.mapToSchoolResponseDTO(classroomDocument.getSchool()),
                classroomDocument.getCreatedAt(),
                classroomDocument.getUpdatedAt()
        );
    }

    public static ClassroomDocument mapToClassroomDocument(ClassroomReqDTO classroomReqDTO){
        return ClassroomDocument.builder()
                .name(classroomReqDTO.getName())
                .school(SchoolDocument.builder().id(classroomReqDTO.getSchoolId()).build())
                .build();
    }

    public static List<ClassroomResDTO> mapToClassroomResponseDTOs(List<ClassroomDocument> classroomDocuments){
        return classroomDocuments.stream().map(ClassroomMapper::mapToClassroomResponseDTO).collect(Collectors.toList());
    }

}
