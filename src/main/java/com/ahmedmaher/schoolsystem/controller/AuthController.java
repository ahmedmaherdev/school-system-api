package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.util.JwtUtil;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = this.userService.registerUser(userDTO);
        UserDetails userDetails = User.builder()
                .username(createdUser.getUsername())
                .password(createdUser.getPassword())
                .authorities((GrantedAuthority) createdUser.getRoles())
                .build();
        String token = jwtUtil.signToken(createdUser);
        Map<String , Object> res = new HashMap<>();
        res.put("user" , createdUser);
        res.put("token" , token);
        return ResponseEntity.ok(res);
    }
}
