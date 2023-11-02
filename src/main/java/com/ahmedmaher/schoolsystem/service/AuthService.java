package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.LoginDTO;
import com.ahmedmaher.schoolsystem.dto.SignupDTO;
import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.model.User;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthService {

    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public UserDTO registerUser(SignupDTO userDTO) {
        HashSet<String> studentRole = new HashSet<>();
        studentRole.add("ROLE_STUDENT");
        userDTO.setRoles(studentRole);
        UserDTO registeredUser = this.userService.createUser(userDTO);
        return registeredUser;
    }

    public UserDTO loginUser(LoginDTO loginDTO){
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            );
            Authentication authentication = this.authenticationManager.authenticate(usernamePassword);
            UserDTO user = this.userService.getUserByUsername(authentication.getName());
            return user;
        }
        catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

}
