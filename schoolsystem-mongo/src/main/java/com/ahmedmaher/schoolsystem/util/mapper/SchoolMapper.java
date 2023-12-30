package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.school.SchoolRequestDTO;
import com.ahmedmaher.schoolsystem.dto.school.SchoolResponseDTO;
import com.ahmedmaher.schoolsystem.document.SchoolDocument;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolMapper {

    public static SchoolResponseDTO mapToSchoolResponseDTO(SchoolDocument schoolDocument){
        return new SchoolResponseDTO(schoolDocument.getId(),
                schoolDocument.getName(),
                schoolDocument.getAddress(),
                schoolDocument.getCreatedAt(),
                schoolDocument.getUpdatedAt()
        );
    }

    public static SchoolDocument mapToSchoolDocument(SchoolRequestDTO schoolRequestDTO){
        SchoolDocument schoolDocument = new SchoolDocument();
        schoolDocument.setName(schoolRequestDTO.getName());
        schoolDocument.setAddress(schoolRequestDTO.getAddress());
        return schoolDocument;
    }

    public static List<SchoolResponseDTO> mapToSchoolResponseDTOs(List<SchoolDocument> schoolDocuments){
        return schoolDocuments.stream().map(SchoolMapper::mapToSchoolResponseDTO).collect(Collectors.toList());
    }

}
