package com.ahmedmaher.schoolsystem.service.enrollment;

import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import com.ahmedmaher.schoolsystem.document.UserDoc;
import com.ahmedmaher.schoolsystem.repository.ClassroomRepo;
import com.ahmedmaher.schoolsystem.repository.UserRepo;
import com.ahmedmaher.schoolsystem.service.classroom.ClassroomService;
import com.ahmedmaher.schoolsystem.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private final UserRepo userRepo;
    private final ClassroomRepo classroomRepo;
    private final ClassroomService classroomService;
    private final UserService userService;

    @Autowired
    public EnrollmentServiceImp(UserRepo userRepo, ClassroomRepo classroomRepo, ClassroomService classroomService, UserService userService) {
        this.userRepo = userRepo;
        this.classroomRepo = classroomRepo;
        this.classroomService = classroomService;
        this.userService = userService;
    }


    @Transactional
    @Override
    public void createEnrollment(String userId, String classroomId) {
        UserDoc userDoc = userService.getOne(userId);
        ClassroomDoc classroomDoc =  classroomService.getOne(classroomId);

        userDoc.addClassroom(classroomDoc);
        classroomDoc.addUser(userDoc);

        classroomRepo.save(classroomDoc);
        userRepo.save(userDoc);
    }

    @Transactional
    @Override
    public void deleteEnrollment(String userId, String classroomId) {
        UserDoc userDoc = userService.getOne(userId);
        ClassroomDoc classroomDoc =  classroomService.getOne(classroomId);

        userDoc.removeClassroom(classroomDoc);
        classroomDoc.removeUser(userDoc);

        classroomRepo.save(classroomDoc);
        userRepo.save(userDoc);
    }

    public List<ClassroomDoc> getUserEnrollments(String userId) {
        UserDoc userDoc = userService.getOne(userId);
        return userDoc.getClassrooms();
    }

    @Override
    public List<UserDoc> getClassroomUsers(String classroomId) {
        ClassroomDoc classroom = classroomService.getOne(classroomId);
        return  classroom.getUsers();
    }
}
