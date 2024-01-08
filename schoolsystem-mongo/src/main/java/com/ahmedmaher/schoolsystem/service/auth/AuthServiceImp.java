package com.ahmedmaher.schoolsystem.service.auth;

import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.exception.BadRequestException;
import com.ahmedmaher.schoolsystem.service.user.UserService;
import com.ahmedmaher.schoolsystem.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImp implements AuthService {


    @Value("${auth.reset-token-expires-in-minutes}")
    private int EXPIRES_IN_MINUTES;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordUtil passwordUtil;

    @Autowired
    public AuthServiceImp(UserService userService,
                          AuthenticationManager authenticationManager,
                          PasswordUtil passwordUtil
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordUtil = passwordUtil;
    }

    public UserDocument registerUser(UserDocument userDocument) {
        Set<UserRole> studentRole = new HashSet<>();
        studentRole.add(UserRole.STUDENT);
        userDocument.setRoles(studentRole);
        return userService.createOne(userDocument);
    }

    public UserDocument loginUser(UserDocument userDocument){
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    userDocument.getUsername(),
                    userDocument.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(usernamePassword);
            return userService.getByUsername(authentication.getName());
        }
        catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

    public void forgetPassword(UserDocument userDocument) {
        UserDocument user = userService.getByEmail(userDocument.getEmail());

        String resetToken = passwordUtil.generateResetToken();
        LocalDateTime resetTokenExpires = LocalDateTime.now().plusMinutes(EXPIRES_IN_MINUTES);

        user.setPasswordResetToken(resetToken);
        user.setPasswordResetExpires(resetTokenExpires);

        userService.saveUser(user);
        System.out.println("https://localhost:3000/reset-password?token=" + resetToken);
    }

    public UserDocument resetPassword(UserDocument userDocument) {
        UserDocument user = userService.getByPasswordResetToken(
                userDocument.getPasswordResetToken()
        );

        if(user == null || passwordUtil.isResetTokenExpired(user.getPasswordResetExpires()))
            throw new BadRequestException("Token is invalid or expired.");

        user.setPassword(passwordUtil.hashPassword(userDocument.getPassword()));
        user.setPasswordResetToken(null);
        user.setPasswordResetExpires(null);

        return userService.saveUser(user);
    }

    public UserDocument updatePassword(UserDocument userDocument, String newPassword) {
        UserDocument user = userService.getByUsername(userDocument.getUsername());

        if(!passwordUtil.correctPassword(userDocument.getPassword() , user.getPassword()))
            throw new BadRequestException("current password is not correct.");

        user.setPassword(passwordUtil.hashPassword(newPassword));
        return userService.saveUser(user);
    }

}
