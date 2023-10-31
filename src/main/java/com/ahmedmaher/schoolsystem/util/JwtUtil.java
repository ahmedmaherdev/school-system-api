package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import com.ahmedmaher.schoolsystem.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expireAt}")
    private long JWT_EXPIRE_AT;

    private JwtParser jwtParser;

    public JwtUtil() {
        this.jwtParser = Jwts.parser().setSigningKey(JWT_SECRET);
    }
    public String signToken(UserDTO user) {
        Claims claims = (Claims) Jwts.builder().setSubject(user.getUsername());
        claims.put("name" , user.getName());
        claims.setId(user.getId() + "");

        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + this.JWT_EXPIRE_AT))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.ES512 , this.JWT_SECRET)
                .compact();
    }

//    public UserDetails verifyToken(String token) {
//
//    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.JWT_SECRET).parseClaimsJws(token);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

}
