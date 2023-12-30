package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.service.FileUploadService;
import com.ahmedmaher.schoolsystem.service.classroom.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserDocument> getAll(Pageable pageable) {
        Page<UserDocument> users = userRepository.findAll(pageable);
        return users.getContent();
    }

    @Override
    public UserDocument getOne(String id) throws NotFoundException {
        UserDocument userEntity = userRepository.findById(id).orElse(null);
        if(userEntity == null) throw new NotFoundException("User not found with id: " + id);
        return userEntity;
    }

    @Override
    public UserDocument getByUsername(String username) throws NotFoundException {
        UserDocument userEntity = userRepository.findByUsername(username);
        if(userEntity == null) throw new NotFoundException("User not found with username: " + username);
        return userEntity;
    }

    @Transactional
    @Override
    public UserDocument createOne(UserDocument document) {
        checkDuplicatedUserNameAndEmail(document);
        String hashedPassword = bCryptPasswordEncoder.encode(document.getPassword());
        document.setPassword(hashedPassword);
        document.setPhoto("default.jpg");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        userRepository.save(document);
        return document;
    }

    @Override
    public UserDocument updateOne(String id, UserDocument document) throws NotFoundException {
        UserDocument userEntity = getOne(id);
        checkDuplicatedUserNameAndEmail(userEntity);

        userEntity.setName(document.getName());
        userEntity.setEmail(document.getEmail());
        userEntity.setUsername(document.getUsername());
        userEntity.setUpdatedAt(LocalDateTime.now());

        userRepository.save(userEntity);
        return userEntity;
    }

    private void checkDuplicatedUserNameAndEmail(UserDocument userEntity) throws NotFoundException {
        if(userRepository.findByEmail(userEntity.getEmail()) != null)
            throw new DuplicatedException("Duplicated email value: " + userEntity.getEmail());

        if(userRepository.findByUsername(userEntity.getUsername()) != null)
            throw new DuplicatedException("Duplicated username value: " + userEntity.getUsername());
    }

    @Override
    public void deleteOne(String id) throws NotFoundException {
        UserDocument deletedUserEntity = userRepository.findById(id).orElse(null);
        if(deletedUserEntity == null) throw new NotFoundException("User not found with id: " + id);
        userRepository.delete(deletedUserEntity);
    }

    @Override
    public List<UserDocument> search(String word , Pageable pageable) {
        return userRepository.searchBy(word, pageable);
    }

    @Override
    public long getAllUsersCount() {
        return userRepository.count();
    }

    @Override
    public UserDocument updateUserPhoto(UserDocument user, MultipartFile photoFile) throws Exception {
        String photo = fileUploadService.saveUserPhoto(photoFile);
        user.setPhoto(photo);
        userRepository.save(user);
        return user;
    }
}