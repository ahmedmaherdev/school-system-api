package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.auth.LoginRequestDTO;
import com.ahmedmaher.schoolsystem.dto.auth.SignupRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.service.AuthService;
import com.ahmedmaher.schoolsystem.util.JwtUtil;
import com.ahmedmaher.schoolsystem.util.UserToken;
import com.ahmedmaher.schoolsystem.util.mapper.AuthMapper;
import com.ahmedmaher.schoolsystem.util.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.config.backend.auth.base-uri}")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("${app.config.backend.auth.api.signup-uri}")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        UserEntity userEntity = AuthMapper.mapSignupDTOToUserEntity(signupRequestDTO);
        UserResponseDTO user = UserMapper.mapUserEntityToUserResponseDTO(
                this.authService.registerUser(userEntity)
        );
        String token = jwtUtil.signToken(user);
        return ResponseEntity.ok(UserToken.generateUserTokenResponse(user , token));
    }

    @PostMapping("${app.config.backend.auth.api.login-uri}")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        UserEntity userEntity = AuthMapper.mapLoginDTOToUserEntity(loginRequestDTO);
        UserResponseDTO user = UserMapper.mapUserEntityToUserResponseDTO(
                this.authService.loginUser(userEntity)
        );
        String token = this.jwtUtil.signToken(user);
        return ResponseEntity.ok(UserToken.generateUserTokenResponse(user , token));
    }
}
