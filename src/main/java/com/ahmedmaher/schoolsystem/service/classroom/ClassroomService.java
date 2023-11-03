package com.ahmedmaher.schoolsystem.service.classroom;

import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomRequestDTO;
import com.ahmedmaher.schoolsystem.dto.classroom.ClassroomResponseDTO;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.SchoolEntity;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import com.ahmedmaher.schoolsystem.repository.SchoolRepository;
import com.ahmedmaher.schoolsystem.service.BaseService;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public interface ClassroomService extends BaseService<ClassroomEntity> {
    List<ClassroomEntity> getAllBySchoolId(long schoolId , Pageable pageable);
    long getAllClassroomsCount(long schoolId);
}
