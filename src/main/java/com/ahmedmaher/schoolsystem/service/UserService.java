package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.LoginDTO;
import com.ahmedmaher.schoolsystem.dto.SignupDTO;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder ,
                       AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
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

    public UserDTO getUserByUsername(String username) throws NotFoundException {
        User user = this.userRepository.findByUsername(username);
        if(user == null) throw new NotFoundException("User not found with username: " + username);
        return Mapper.mapUserToUserDTO(user);
    }

    @Transactional
    public UserDTO createUser(SignupDTO userDTO){
        User user =  new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());

        String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        user.setPassword(hashedPassword);

        Set<String> userDTORoles = userDTO.getRoles();
        if( userDTORoles == null) {
            // set student role as a default if not provided
            userDTORoles = new HashSet<>();
            userDTORoles.add("ROLE_STUDENT");
        }

        Set<Role> userRoles = new HashSet<>();
        for (String role : userDTORoles ) {
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


}
