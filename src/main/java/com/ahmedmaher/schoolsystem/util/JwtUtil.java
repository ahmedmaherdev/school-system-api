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
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TimeUnit.DAYS.toMillis(this.JWT_EXPIRE_AT));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiredDate)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256 , this.JWT_SECRET)
                .compact();
    }

    public Claims verifyToken(HttpServletRequest req) throws Exception {
        try {
            String token = this.splitToken(req);
            if(token == null)
                throw new UnauthorizedException("Invalid token.");
            Claims claims =  (Claims) Jwts.parser().setSigningKey(JWT_SECRET).parse(token).getBody();
            if(claims.getExpiration().before(new Date()))
                throw new UnauthorizedException("Expired token");
            return claims;
        }catch (Exception ex) {
            throw new UnauthorizedException("Invalid token.");
        }
    }

    private String splitToken(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer")) {
            return null;
        }
        token = token.split(" ")[1];
        return token;
    }


}
