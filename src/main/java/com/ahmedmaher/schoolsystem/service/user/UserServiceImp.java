package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.entity.ClassroomEntity;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.service.FileUploadService;
import com.ahmedmaher.schoolsystem.service.classroom.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private  final ClassroomService classroomService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileUploadService fileUploadService;

    @Autowired
    public UserServiceImp(UserRepository userRepository, ClassroomService classroomService, BCryptPasswordEncoder bCryptPasswordEncoder, FileUploadService fileUploadService) {
        this.userRepository = userRepository;
        this.classroomService = classroomService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fileUploadService = fileUploadService;
    }



    @Override
    public List<UserEntity> getAll(Pageable pageable) {
        Page<UserEntity> users = userRepository.findAll(pageable);
        return users.getContent();
    }

    @Override
    public UserEntity getOne(long id) throws NotFoundException {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity == null) throw new NotFoundException("User not found with id: " + id);
        return userEntity;
    }

    @Override
    public UserEntity getByUsername(String username) throws NotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) throw new NotFoundException("User not found with username: " + username);
        return userEntity;
    }

    @Transactional
    @Override
    public UserEntity createOne(UserEntity entity) {
        String hashedPassword = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(hashedPassword);
        entity.setPhoto("default.jpg");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        try {
            userRepository.save(entity);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedException("Duplicated fields: please, provide another value for username or email.");
        }
        return entity;
    }

    @Override
    public UserEntity updateOne(long id, UserEntity entity) throws NotFoundException {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity == null)
            throw new NotFoundException("User not found with id: " + id);
        userEntity.setName(entity.getName());
        userEntity.setEmail(entity.getEmail());
        userEntity.setUsername(entity.getUsername());
        userEntity.setUpdatedAt(LocalDateTime.now());
        try {
            userRepository.save(userEntity);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedException("Duplicated fields: please, provide another value for username or email.");
        }
        return userEntity;
    }

    @Override
    public void deleteOne(long id) throws NotFoundException {
        UserEntity deletedUserEntity = userRepository.findById(id).orElse(null);
        if(deletedUserEntity == null) throw new NotFoundException("User not found with id: " + id);
        userRepository.delete(deletedUserEntity);
    }

    @Override
    public List<UserEntity> search(String word , Pageable pageable) {
        return userRepository.searchBy(word, pageable);
    }

    @Override
    public long getAllUsersCount() {
        return userRepository.count();
    }

    @Override
    public List<ClassroomEntity> getStudentEnrollments(long userId) {
        UserEntity userEntity = userRepository.findStudentWithEnrollments(userId);
        return userEntity.getClassrooms();
    }

    @Transactional
    @Override
    public void createStudentEnrollment(long userId, long classroomId) {
        UserEntity userEntity = this.getOne(userId);
        ClassroomEntity classroomEntity =  classroomService.getOne(classroomId);
        userEntity.addClassroom(classroomEntity);

        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public void deleteStudentEnrollment(long userId, long classroomId) {
        UserEntity userEntity = this.getOne(userId);
        ClassroomEntity classroomEntity =  classroomService.getOne(classroomId);
        userEntity.removeClassroom(classroomEntity);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity updateUserPhoto(UserEntity user, MultipartFile photoFile) throws Exception {
        String photo = fileUploadService.saveUserPhoto(photoFile);
        user.setPhoto(photo);
        userRepository.save(user);
        return user;
    }
}