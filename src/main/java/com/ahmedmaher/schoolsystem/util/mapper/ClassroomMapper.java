package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomRequestDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ClassroomMapper {

    public static ClassroomResponseDTO mapClassroomEntityToClassroomResponseDTO(ClassroomEntity classroomEntity){
        return new ClassroomResponseDTO(
                classroomEntity.getId(),
                classroomEntity.getName(),
                classroomEntity.getCapacity(),
                classroomEntity.getSchoolEntity().getId(),
                classroomEntity.getCreatedAt(),
                classroomEntity.getUpdatedAt()
        );
    }

    public static ClassroomEntity mapClassroomRequestToClassroomEntity(ClassroomRequestDTO classroomRequestDTO){
        ClassroomEntity classroomEntity = new ClassroomEntity();
        classroomEntity.setName(classroomRequestDTO.getName());
        return classroomEntity;
    }

    public static List<ClassroomResponseDTO> mapClassroomEntitiesToClassroomResponseDTOs(List<ClassroomEntity> classroomEntities){
        return classroomEntities.stream().map(ClassroomMapper::mapClassroomEntityToClassroomResponseDTO).collect(Collectors.toList());
    }

}
