package com.ahmedmaher.schoolsystem.service.user;


import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImp(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public List<UserEntity> getAll(Pageable pageable) {
        Page<UserEntity> users = this.userRepository.findAll(pageable);
        return users.getContent();
    }

    @Override
    public UserEntity getOne(long id) throws NotFoundException {
        UserEntity userEntity = this.userRepository.findById(id).orElse(null);
        if(userEntity == null) throw new NotFoundException("User not found with id: " + id);
        return userEntity;
    }

    @Override
    public UserEntity getByUsername(String username) throws NotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(username);
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
            this.userRepository.save(entity);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedException("Duplicated fields: please, provide another value for username or email.");
        }
        return entity;
    }

    @Override
    public UserEntity updateOne(long id, UserEntity entity) throws NotFoundException {
        if(!this.userRepository.existsById(id))
            throw new NotFoundException("User not found with id: " + id);
        entity.setId(id);
        entity.setUpdatedAt(LocalDateTime.now());
        try {
            this.userRepository.save(entity);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedException("Duplicated fields: please, provide another value for username or email.");
        }
        return entity;
    }

    @Override
    public void deleteOne(long id) throws NotFoundException {
        UserEntity deletedUserEntity = this.userRepository.findById(id).orElse(null);
        if(deletedUserEntity == null) throw new NotFoundException("User not found with id: " + id);
        this.userRepository.delete(deletedUserEntity);
    }

    @Override
    public long getAllUsersCount() {
        return this.userRepository.count();
    }
}