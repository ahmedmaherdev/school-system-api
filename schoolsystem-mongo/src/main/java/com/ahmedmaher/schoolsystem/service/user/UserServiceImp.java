package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.service.FileUploadService;
import com.ahmedmaher.schoolsystem.util.PasswordUtil;
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
    private final PasswordUtil passwordUtil;
    private final FileUploadService fileUploadService;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordUtil passwordUtil, FileUploadService fileUploadService) {
        this.userRepository = userRepository;
        this.passwordUtil = passwordUtil;
        this.fileUploadService = fileUploadService;
    }



    @Override
    public List<UserDocument> getAll(Pageable pageable) {
        Page<UserDocument> users = userRepository.findAll(pageable);
        return users.getContent();
    }

    @Override
    public UserDocument getOne(String id) throws NotFoundException {
        UserDocument user = userRepository.findById(id).orElse(null);
        if(user == null) throw new NotFoundException("User not found with id: " + id);
        return user;
    }

    @Override
    public UserDocument getByUsername(String username) throws NotFoundException {
        UserDocument user = userRepository.findByUsername(username);
        if(user == null) throw new NotFoundException("User not found with username: " + username);
        return user;
    }

    @Override
    public UserDocument getByEmail(String email) throws NotFoundException {
        UserDocument user = userRepository.findByEmail(email);
        if(user == null) throw new NotFoundException("User not found with email: " + email);
        return user;
    }

    @Override
    public UserDocument getByPasswordResetToken(String passwordResetToken) {
        return userRepository.findByPasswordResetToken(passwordResetToken);
    }

    @Transactional
    @Override
    public UserDocument createOne(UserDocument document) {
        checkDuplicatedUserNameAndEmail(document);
        document.setPassword(passwordUtil.hashPassword(document.getPassword()));
        document.setPhoto("default.jpg");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        userRepository.save(document);
        return document;
    }

    @Override
    public UserDocument updateOne(String id, UserDocument document) throws NotFoundException {
        UserDocument user = getOne(id);
        checkDuplicatedUserNameAndEmail(document);

        user.setName(document.getName());
        user.setEmail(document.getEmail());
        user.setUsername(document.getUsername());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }

    private void checkDuplicatedUserNameAndEmail(UserDocument user) throws NotFoundException {
        if(userRepository.findByEmail(user.getEmail()) != null)
            throw new DuplicatedException("Duplicated email value: " + user.getEmail());

        if(userRepository.findByUsername(user.getUsername()) != null)
            throw new DuplicatedException("Duplicated username value: " + user.getUsername());
    }

    @Override
    public void deleteOne(String id) throws NotFoundException {
        UserDocument deletedUser = userRepository.findById(id).orElse(null);
        if(deletedUser == null) throw new NotFoundException("User not found with id: " + id);
        userRepository.delete(deletedUser);
    }

    @Override
    public List<UserDocument> search(String name, Pageable pageable) {
        return userRepository.searchByName(name, pageable);
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

    @Transactional
    @Override
    public UserDocument saveUser(UserDocument user) {
        return userRepository.save(user);
    }
}