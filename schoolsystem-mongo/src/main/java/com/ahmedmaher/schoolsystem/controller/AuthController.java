package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.auth.*;
import com.ahmedmaher.schoolsystem.document.UserDocument;
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
        UserDocument userDocument = AuthMapper.mapToUserDocument(signupReqDTO);
        UserDocument user = this.authService.registerUser(userDocument);
        return userTokenUtil.generateUserTokenResponse(user);
    }

    @PostMapping("${app.config.backend.auth.api.login-uri}")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqDTO loginReqDTO) {
        UserDocument userDocument = AuthMapper.mapToUserDocument(loginReqDTO);
        UserDocument user = this.authService.loginUser(userDocument);
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
        UserDocument userDocument = AuthMapper.mapToUserDocument(resetPasswordReqDTO);
        userDocument.setPasswordResetToken(token);
        UserDocument user = authService.resetPassword(userDocument);
        return userTokenUtil.generateUserTokenResponse(user);
    }

    @PatchMapping("${app.config.backend.auth.api.update-password-uri}")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody UpdatePasswordReqDTO updatePasswordReqDTO,
            Authentication authentication) {

        UserDocument userDocument = AuthMapper.mapToUserDocument(updatePasswordReqDTO);
        userDocument.setUsername((String) authentication.getPrincipal());

        UserDocument user = authService.updatePassword(userDocument , updatePasswordReqDTO.getNewPassword());
        return userTokenUtil.generateUserTokenResponse(user);
    }
}
