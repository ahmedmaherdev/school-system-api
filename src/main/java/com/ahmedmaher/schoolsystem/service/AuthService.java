package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.auth.LoginRequestDTO;
import com.ahmedmaher.schoolsystem.dto.auth.SignupRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.service.user.UserService;
import com.ahmedmaher.schoolsystem.util.mapper.AuthMapper;
import com.ahmedmaher.schoolsystem.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public UserEntity registerUser(UserEntity userEntity) {
        Set<UserRole> studentRole = new HashSet<>();
        studentRole.add(UserRole.STUDENT);
        userEntity.setRoles(studentRole);
        return this.userService.createOne(userEntity);
    }

    public UserEntity loginUser(UserEntity userEntity){
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    userEntity.getUsername(),
                    userEntity.getPassword()
            );
            Authentication authentication = this.authenticationManager.authenticate(usernamePassword);
            return this.userService.getByUsername(authentication.getName());
        }
        catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

}
