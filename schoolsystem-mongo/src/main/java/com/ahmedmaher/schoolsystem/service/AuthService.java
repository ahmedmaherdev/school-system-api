package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.service.user.UserService;
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

    public UserDocument registerUser(UserDocument userDocument) {
        Set<UserRole> studentRole = new HashSet<>();
        studentRole.add(UserRole.STUDENT);
        userDocument.setRoles(studentRole);
        return this.userService.createOne(userDocument);
    }

    public UserDocument loginUser(UserDocument userDocument){
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    userDocument.getUsername(),
                    userDocument.getPassword()
            );
            Authentication authentication = this.authenticationManager.authenticate(usernamePassword);
            return this.userService.getByUsername(authentication.getName());
        }
        catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

}
