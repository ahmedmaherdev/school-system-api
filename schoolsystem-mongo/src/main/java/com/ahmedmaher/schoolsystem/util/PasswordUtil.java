package com.ahmedmaher.schoolsystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PasswordUtil {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String hashPassword(String plain) {
        return bCryptPasswordEncoder.encode(plain);
    }

    public boolean correctPassword(String plain, String hashed) {
        return bCryptPasswordEncoder.matches(plain , hashed);
    }

    public String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    public boolean isResetTokenExpired(LocalDateTime expireDate) {
        return expireDate.isBefore(LocalDateTime.now());
    }
}
