package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.school.SchoolReqDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolResDTO;
import com.ahmedmaher.schoolsystem.document.SchoolDocument;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolMapper {

    public static SchoolResDTO mapToSchoolResponseDTO(SchoolDocument schoolDocument){
        return new SchoolResDTO(schoolDocument.getId(),
                schoolDocument.getName(),
                schoolDocument.getAddress(),
                schoolDocument.getCreatedAt(),
                schoolDocument.getUpdatedAt()
        );
    }

    public static SchoolDocument mapToSchoolDocument(SchoolReqDTO schoolReqDTO){
        return SchoolDocument.builder()
                .name(schoolReqDTO.getName())
                .address(schoolReqDTO.getAddress())
                .build();
    }

    public static List<SchoolResDTO> mapToSchoolResponseDTOs(List<SchoolDocument> schoolDocuments){
        return schoolDocuments.stream().map(SchoolMapper::mapToSchoolResponseDTO).collect(Collectors.toList());
    }

}
