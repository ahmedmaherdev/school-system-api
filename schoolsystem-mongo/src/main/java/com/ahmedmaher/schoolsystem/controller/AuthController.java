package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.auth.*;
import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.service.AuthService;
import com.ahmedmaher.schoolsystem.util.JwtUtil;
import com.ahmedmaher.schoolsystem.util.UserTokenUtil;
import com.ahmedmaher.schoolsystem.util.mapper.AuthMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${app.config.backend.auth.base-uri}")
public class AuthController {
    private final AuthService authService;
    private final UserTokenUtil userTokenUtil;

    @Autowired
    public AuthController(AuthService authService, UserTokenUtil userTokenUtil) {
        this.authService = authService;
        this.userTokenUtil = userTokenUtil;
    }

    @PostMapping("${app.config.backend.auth.api.signup-uri}")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        UserDocument userDocument = AuthMapper.mapToUserDocument(signupRequestDTO);
        UserDocument user = this.authService.registerUser(userDocument);
        return userTokenUtil.generateUserTokenResponse(user);
    }

    @PostMapping("${app.config.backend.auth.api.login-uri}")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        UserDocument userDocument = AuthMapper.mapToUserDocument(loginRequestDTO);
        UserDocument user = this.authService.loginUser(userDocument);
        return userTokenUtil.generateUserTokenResponse(user);

    }


    @PatchMapping("${app.config.backend.auth.api.forget-password-uri}")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequestDTO forgetPasswordRequestDTO ) {
        authService.forgetPassword(AuthMapper.mapToUserDocument(forgetPasswordRequestDTO));
        Map<String, String> res = new HashMap<>();
        res.put("status" , "success");
        res.put("message", "Token is sent to your email.");
        return ResponseEntity.ok(res);
    }

    @PatchMapping("${app.config.backend.auth.api.reset-password-uri}")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO,
            @RequestParam("token") String token) {
        UserDocument userDocument = AuthMapper.mapToUserDocument(resetPasswordRequestDTO);
        userDocument.setPasswordResetToken(token);
        UserDocument user = authService.resetPassword(userDocument);
        return userTokenUtil.generateUserTokenResponse(user);
    }

    @PatchMapping("${app.config.backend.auth.api.update-password-uri}")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO,
            Authentication authentication) {

        UserDocument userDocument = AuthMapper.mapToUserDocument(updatePasswordRequestDTO);
        userDocument.setUsername((String) authentication.getPrincipal());

        UserDocument user = authService.updatePassword(userDocument , updatePasswordRequestDTO.getNewPassword());
        return userTokenUtil.generateUserTokenResponse(user);
    }
}
