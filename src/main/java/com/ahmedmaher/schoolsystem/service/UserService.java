package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.model.Role;
import com.ahmedmaher.schoolsystem.model.User;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDTO> userDTOs =  users.stream().map(Mapper::mapUserToUserDTO).collect(Collectors.toList());
        return userDTOs;
    }

    public UserDTO getUserById(Long userId) throws NotFoundException {
        User user = this.userRepository.findById(userId).orElse(null);
        if(user == null) throw new NotFoundException("User not found with id: " + userId);
        return Mapper.mapUserToUserDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO){
        User user =  new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());

        Set<Role> userSet = new HashSet<>();
        Role userRole = new Role();
        userRole.setId(1);
        userRole.setName("ROLE_STUDENT");
        userSet.add(userRole);
        user.setRoles(userSet);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        try {
            this.userRepository.save(user);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedException("Duplicated fields: please, provide another value for username or email.");
        }
        return Mapper.mapUserToUserDTO(user);
    }

    @Transactional
    public UserDTO updateUser(long userId, UserDTO userDTO) throws NotFoundException{
        User selectedUser = this.userRepository.findById(userId).orElse(null);
        if(selectedUser == null) throw new NotFoundException("User not found with id: " + userId);

        selectedUser.setEmail(userDTO.getEmail());
        selectedUser.setName(userDTO.getName());
        selectedUser.setUsername(userDTO.getUsername());

        selectedUser.setUpdatedAt(LocalDateTime.now());

        try {
            this.userRepository.save(selectedUser);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedException("Duplicated fields: please, provide another value for username or email.");
        }
        return Mapper.mapUserToUserDTO(selectedUser);
    }

    @Transactional
    public boolean deleteUser(long userId) throws NotFoundException{
        User deletedUser = this.userRepository.findById(userId).orElse(null);
        if(deletedUser == null) throw new NotFoundException("User not found with id: " + userId);
        this.userRepository.delete(deletedUser);
        return true;
    }

    public long getAllUsersCount() {
        return this.userRepository.count();
    }

}
