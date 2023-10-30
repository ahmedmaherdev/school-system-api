package com.ahmedmaher.schoolsystem.service;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class TokenService {

    @Value("${jwt.secret}")
    private static String JWT_SECRET;

    @Value("${jwt.expireAt}")
    private static long JWT_EXPIREAT;


    public static String signToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + TokenService.JWT_EXPIREAT))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.ES512 , TokenService.JWT_SECRET)
                .compact();
    }
}
