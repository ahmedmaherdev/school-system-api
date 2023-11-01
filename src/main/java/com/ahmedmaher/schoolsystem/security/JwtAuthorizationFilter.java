package com.ahmedmaher.schoolsystem.security;

import com.ahmedmaher.schoolsystem.exception.UnauthorizedException;
import com.ahmedmaher.schoolsystem.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;

    @Autowired
    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = this.jwtUtil.splitToken(request);
            if(token == null) {
                filterChain.doFilter(request,response);
                return;
            }
            Claims claims = this.jwtUtil.verifyToken(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject() , "" , new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }
}
