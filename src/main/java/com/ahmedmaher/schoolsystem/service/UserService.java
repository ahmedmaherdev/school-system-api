package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.model.User;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        List<UserDTO> userDTOs =  users.stream().map(this::mapUserToUserDTO).collect(Collectors.toList());
        return userDTOs;
    }

    public UserDTO getUserById(Long userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if(user != null) return mapUserToUserDTO(user);

        return null;
    }

    private UserDTO mapUserToUserDTO(User user){
        return new UserDTO(user.getId(),
                user.getName(),
                user.getUsername() ,
                user.getEmail() ,
                user.getCreatedAt() ,
                user.getUpdatedAt()
        );
    }
}
