package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.auth.*;
import com.ahmedmaher.schoolsystem.document.UserDoc;
import com.ahmedmaher.schoolsystem.service.auth.AuthService;
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
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDTO signupReqDTO) {
        UserDoc userDoc = AuthMapper.mapToUserDocument(signupReqDTO);
        UserDoc user = this.authService.registerUser(userDoc);
        return userTokenUtil.generateUserTokenResponse(user);
    }

    @PostMapping("${app.config.backend.auth.api.login-uri}")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqDTO loginReqDTO) {
        UserDoc userDoc = AuthMapper.mapToUserDocument(loginReqDTO);
        UserDoc user = this.authService.loginUser(userDoc);
        return userTokenUtil.generateUserTokenResponse(user);

    }


    @PatchMapping("${app.config.backend.auth.api.forget-password-uri}")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordReqDTO forgetPasswordReqDTO) {
        authService.forgetPassword(AuthMapper.mapToUserDocument(forgetPasswordReqDTO));
        Map<String, String> res = new HashMap<>();
        res.put("status" , "success");
        res.put("message", "Token is sent to your email.");
        return ResponseEntity.ok(res);
    }

    @PatchMapping("${app.config.backend.auth.api.reset-password-uri}")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordReqDTO resetPasswordReqDTO,
            @RequestParam("token") String token) {
        UserDoc userDoc = AuthMapper.mapToUserDocument(resetPasswordReqDTO);
        userDoc.setPasswordResetToken(token);
        UserDoc user = authService.resetPassword(userDoc);
        return userTokenUtil.generateUserTokenResponse(user);
    }

    @PatchMapping("${app.config.backend.auth.api.update-password-uri}")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody UpdatePasswordReqDTO updatePasswordReqDTO,
            Authentication authentication) {
        UserDoc userDoc = AuthMapper.mapToUserDocument(updatePasswordReqDTO);
        userDoc.setUsername((String) authentication.getPrincipal());

        UserDoc user = authService.updatePassword(userDoc, updatePasswordReqDTO.getPassword());
        return userTokenUtil.generateUserTokenResponse(user);
    }
}
