package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.LoginDTO;
import com.ahmedmaher.schoolsystem.dto.SignupDTO;
import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.service.AuthService;
import com.ahmedmaher.schoolsystem.util.JwtUtil;
import com.ahmedmaher.schoolsystem.service.UserService;
import com.ahmedmaher.schoolsystem.util.UserToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private AuthService authService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupDTO userDTO) {
        UserDTO createdUser = this.authService.registerUser(userDTO);
        String token = jwtUtil.signToken(createdUser);
        return ResponseEntity.ok(UserToken.generateUserTokenResponse(createdUser , token));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        UserDTO user = this.authService.loginUser(loginDTO);
        String token = this.jwtUtil.signToken(user);
        return ResponseEntity.ok(UserToken.generateUserTokenResponse(user , token));
    }
}
