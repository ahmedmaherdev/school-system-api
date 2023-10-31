package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.exception.UnauthorizedException;
import com.ahmedmaher.schoolsystem.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
public class JwtUtil {
    private final String JWT_SECRET = "EDkSgdGptvg70eQXVrt3MPt46e3qG0";
    private final long JWT_EXPIRE_AT = 10;

    public String signToken(UserDTO user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("name" , user.getName());
        claims.setId(user.getId() + "");

        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date( new Date().getTime() + TimeUnit.DAYS.toMillis(this.JWT_EXPIRE_AT)))
                .signWith(SignatureAlgorithm.HS256 , this.JWT_SECRET)
                .compact();
    }

    public Claims verifyToken(HttpServletRequest req) throws Exception {
        try {
            String token = req.getHeader("Authorization");
            if(token == null || !token.startsWith("Bearer")) {
                throw new UnauthorizedException("Invalid token.");
            }
            token = token.split(" ")[1];
            return  (Claims) Jwts.parser().setSigningKey(JWT_SECRET).parse(token).getBody();
        }catch (Exception ex) {
            throw new UnauthorizedException("Invalid token.");
        }
    }


}
