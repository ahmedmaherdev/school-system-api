package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.school.SchoolRequestDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolMapper {

    public static SchoolResponseDTO mapToSchoolResponseDTO(SchoolEntity schoolEntity){
        return new SchoolResponseDTO(schoolEntity.getId(),
                schoolEntity.getName(),
                schoolEntity.getAddress(),
                schoolEntity.getCreatedAt(),
                schoolEntity.getUpdatedAt()
        );
    }

    public static SchoolEntity mapToSchoolEntity(SchoolRequestDTO schoolRequestDTO){
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setName(schoolRequestDTO.getName());
        schoolEntity.setAddress(schoolRequestDTO.getAddress());
        return schoolEntity;
    }

    public static List<SchoolResponseDTO> mapToSchoolResponseDTOs(List<SchoolEntity> schoolEntities){
        return schoolEntities.stream().map(SchoolMapper::mapToSchoolResponseDTO).collect(Collectors.toList());
    }

}
