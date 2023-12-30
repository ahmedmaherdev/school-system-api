package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomRequestDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.SchoolDocument;

import java.util.List;
import java.util.stream.Collectors;

public class ClassroomMapper {

    public static ClassroomResponseDTO mapToClassroomResponseDTO(ClassroomDocument classroomDocument){
        return new ClassroomResponseDTO(
                classroomDocument.getId(),
                classroomDocument.getName(),
                classroomDocument.getCapacity(),
                SchoolMapper.mapToSchoolResponseDTO(classroomDocument.getSchool()),
                classroomDocument.getCreatedAt(),
                classroomDocument.getUpdatedAt()
        );
    }

    public static ClassroomDocument mapToClassroomDocument(ClassroomRequestDTO classroomRequestDTO){
        SchoolDocument schoolDocument = new SchoolDocument();
        schoolDocument.setId(classroomRequestDTO.getSchoolId());

        ClassroomDocument classroomDocument = new ClassroomDocument();
        classroomDocument.setName(classroomRequestDTO.getName());
        classroomDocument.setSchool(schoolDocument);
        return classroomDocument;
    }

    public static List<ClassroomResponseDTO> mapToClassroomResponseDTOs(List<ClassroomDocument> classroomDocuments){
        return classroomDocuments.stream().map(ClassroomMapper::mapToClassroomResponseDTO).collect(Collectors.toList());
    }

}
