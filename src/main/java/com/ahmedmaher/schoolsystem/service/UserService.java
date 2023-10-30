package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.model.Role;
import com.ahmedmaher.schoolsystem.model.User;
import com.ahmedmaher.schoolsystem.repository.RoleRepository;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = this.userRepository.findAll(pageable);
        List<UserDTO> userDTOs =  users.getContent().stream().map(Mapper::mapUserToUserDTO).collect(Collectors.toList());
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

        String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        user.setPassword(hashedPassword);

        Set<Role> userRoles = new HashSet<>();
        for (String role :userDTO.getRoles() ) {
            Role userRole = this.roleRepository.getRoleByName(role);
            if(userRole != null) userRoles.add(userRole);
        }
        user.setRoles(userRoles);
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

    public UserDTO registerUser(UserDTO userDTO) {
        HashSet<String> studentRole = new HashSet<>();
        studentRole.add("ROLE_STUDENT");
        userDTO.setRoles(studentRole);
        UserDTO registeredUser = this.createUser(userDTO);
        return registeredUser;
    }

}
