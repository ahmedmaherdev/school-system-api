package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.document.UserDoc;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.UserRepo;
import com.ahmedmaher.schoolsystem.service.fileUpload.FileUploadService;
import com.ahmedmaher.schoolsystem.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepo userRepo;
    private final PasswordUtil passwordUtil;
    private final FileUploadService fileUploadService;

    @Autowired
    public UserServiceImp(UserRepo userRepo, PasswordUtil passwordUtil, FileUploadService fileUploadService) {
        this.userRepo = userRepo;
        this.passwordUtil = passwordUtil;
        this.fileUploadService = fileUploadService;
    }



    @Override
    public List<UserDoc> getAll(Pageable pageable) {
        Page<UserDoc> users = userRepo.findAll(pageable);
        return users.getContent();
    }

    @Override
    public UserDoc getOne(String id) throws NotFoundException {
        UserDoc user = userRepo.findById(id).orElse(null);
        if(user == null) throw new NotFoundException("User not found with id: " + id);
        return user;
    }

    @Override
    public UserDoc getByUsername(String username) throws NotFoundException {
        UserDoc user = userRepo.findByUsername(username);
        if(user == null) throw new NotFoundException("User not found with username: " + username);
        return user;
    }

    @Override
    public UserDoc getByEmail(String email) throws NotFoundException {
        UserDoc user = userRepo.findByEmail(email);
        if(user == null) throw new NotFoundException("User not found with email: " + email);
        return user;
    }

    @Override
    public UserDoc getByPasswordResetToken(String passwordResetToken) {
        return userRepo.findByPasswordResetToken(passwordResetToken);
    }

    @Transactional
    @Override
    public UserDoc createOne(UserDoc document) {
        checkDuplicatedUserNameAndEmail(document);
        document.setPassword(passwordUtil.hashPassword(document.getPassword()));
        document.setPhoto("default.jpg");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        userRepo.save(document);
        return document;
    }

    @Override
    public UserDoc updateOne(String id, UserDoc document) throws NotFoundException {
        UserDoc user = getOne(id);
        checkDuplicatedUserNameAndEmail(document);

        user.setName(document.getName());
        user.setEmail(document.getEmail());
        user.setUsername(document.getUsername());
        user.setUpdatedAt(LocalDateTime.now());

        userRepo.save(user);
        return user;
    }

    private void checkDuplicatedUserNameAndEmail(UserDoc user) throws NotFoundException {
        if(userRepo.findByEmail(user.getEmail()) != null)
            throw new DuplicatedException("Duplicated email value: " + user.getEmail());

        if(userRepo.findByUsername(user.getUsername()) != null)
            throw new DuplicatedException("Duplicated username value: " + user.getUsername());
    }

    @Override
    public void deleteOne(String id) throws NotFoundException {
        UserDoc deletedUser = userRepo.findById(id).orElse(null);
        if(deletedUser == null) throw new NotFoundException("User not found with id: " + id);
        userRepo.delete(deletedUser);
    }

    @Override
    public List<UserDoc> search(String name, Pageable pageable) {
        return userRepo.searchByName(name, pageable);
    }

    @Override
    public long getAllUsersCount() {
        return userRepo.count();
    }


    @Override
    public UserDoc updateUserPhoto(UserDoc user, MultipartFile photoFile) throws Exception {
        String photo = fileUploadService.saveUserPhoto(photoFile);
        user.setPhoto(photo);
        userRepo.save(user);
        return user;
    }

    @Transactional
    @Override
    public UserDoc saveUser(UserDoc user) {
        return userRepo.save(user);
    }
}