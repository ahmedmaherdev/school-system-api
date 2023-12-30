package com.ahmedmaher.schoolsystem.service.enrollment;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepository;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.service.classroom.ClassroomService;
import com.ahmedmaher.schoolsystem.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    private final ClassroomService classroomService;
    private final UserService userService;

    @Autowired
    public EnrollmentServiceImp(UserRepository userRepository, ClassroomRepository classroomRepository, ClassroomService classroomService, UserService userService) {
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.classroomService = classroomService;
        this.userService = userService;
    }


    @Transactional
    @Override
    public void createEnrollment(String userId, String classroomId) {
        UserDocument userEntity = userService.getOne(userId);
        ClassroomDocument classroomEntity =  classroomService.getOne(classroomId);

        userEntity.addClassroom(classroomEntity);
        classroomEntity.addUser(userEntity);

        classroomRepository.save(classroomEntity);
        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public void deleteEnrollment(String userId, String classroomId) {
        UserDocument userEntity = userService.getOne(userId);
        ClassroomDocument classroomEntity =  classroomService.getOne(classroomId);

        userEntity.removeClassroom(classroomEntity);
        classroomEntity.removeUser(userEntity);

        classroomRepository.save(classroomEntity);
        userRepository.save(userEntity);
    }

    public List<ClassroomDocument> getUserEnrollments(String userId) {
        UserDocument userEntity = userService.getOne(userId);
        return userEntity.getClassrooms();
    }
}
