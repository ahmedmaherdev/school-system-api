package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.dto.LoginRequestDTO;
import com.ahmedmaher.schoolsystem.dto.SignupRequestDTO;
import com.ahmedmaher.schoolsystem.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public UserResponseDTO registerUser(SignupRequestDTO userDTO) {
        HashSet<String> studentRole = new HashSet<>();
        studentRole.add("ROLE_STUDENT");
        userDTO.setRoles(studentRole);
        return this.userService.createUser(userDTO);
    }

    public UserResponseDTO loginUser(LoginRequestDTO loginRequestDTO){
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername(),
                    loginRequestDTO.getPassword()
            );
            Authentication authentication = this.authenticationManager.authenticate(usernamePassword);
            return this.userService.getUserByUsername(authentication.getName());
        }
        catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

}
