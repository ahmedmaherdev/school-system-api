package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.service.TokenService;
import com.ahmedmaher.schoolsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = this.userService.registerUser(userDTO);
        UserDetails userDetails = User.builder()
                .username(createdUser.getUsername())
                .password(createdUser.getPassword())
                .authorities((GrantedAuthority) createdUser.getRoles())
                .build();
        String token = TokenService.signToken(userDetails);
        Map<String , Object> res = new HashMap<>();
        res.put("user" , createdUser);
        res.put("token" , token);
        return ResponseEntity.ok(res);
    }
}
