package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
public class JwtUtil {

    private  String JWT_SECRET;

    private final long JWT_EXPIRE_AT;

    public JwtUtil( @Value("${jwt.secret}")String jwtSecret,
                   @Value("${jwt.expireAt}") long jwtExpireAt
    ) {
        this.JWT_SECRET = jwtSecret;
        this.JWT_EXPIRE_AT = jwtExpireAt;
    }

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

    public Claims verifyToken(String token) throws Exception {
        try {
            Claims claims =  (Claims) Jwts.parser().setSigningKey(JWT_SECRET).parse(token).getBody();
            if(claims.getExpiration().before(new Date()))
                throw new UnauthorizedException("Expired token");
            return claims;
        }catch (Exception ex) {
            throw new UnauthorizedException("Invalid token.");
        }
    }

    public String splitToken(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer")) {
            return null;
        }
        token = token.split(" ")[1];
        return token;
    }


}
