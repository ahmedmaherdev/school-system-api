package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.school.SchoolReqDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolResDTO;
import com.ahmedmaher.schoolsystem.document.SchoolDoc;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolMapper {

    public static SchoolResDTO mapToSchoolResponseDTO(SchoolDoc schoolDoc){
        return new SchoolResDTO(schoolDoc.getId(),
                schoolDoc.getName(),
                schoolDoc.getAddress(),
                schoolDoc.getCreatedAt(),
                schoolDoc.getUpdatedAt()
        );
    }

    public static SchoolDoc mapToSchoolDocument(SchoolReqDTO schoolReqDTO){
        return SchoolDoc.builder()
                .name(schoolReqDTO.getName())
                .address(schoolReqDTO.getAddress())
                .build();
    }

    public static List<SchoolResDTO> mapToSchoolResponseDTOs(List<SchoolDoc> schoolDocs){
        return schoolDocs.stream().map(SchoolMapper::mapToSchoolResponseDTO).collect(Collectors.toList());
    }

}
