package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.LoginRequestDTO;
import com.ahmedmaher.schoolsystem.dto.SignupRequestDTO;
import com.ahmedmaher.schoolsystem.dto.UserResponseDTO;
import com.ahmedmaher.schoolsystem.service.AuthService;
import com.ahmedmaher.schoolsystem.util.JwtUtil;
import com.ahmedmaher.schoolsystem.util.UserToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO userDTO) {
        UserResponseDTO createdUser = this.authService.registerUser(userDTO);
        String token = jwtUtil.signToken(createdUser);
        return ResponseEntity.ok(UserToken.generateUserTokenResponse(createdUser , token));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        UserResponseDTO user = this.authService.loginUser(loginRequestDTO);
        String token = this.jwtUtil.signToken(user);
        return ResponseEntity.ok(UserToken.generateUserTokenResponse(user , token));
    }
}
