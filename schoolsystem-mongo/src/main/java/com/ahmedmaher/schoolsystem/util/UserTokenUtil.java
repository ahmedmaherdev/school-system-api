package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.document.UserDoc;
import com.ahmedmaher.schoolsystem.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserTokenUtil {

    @Autowired
    private  JwtUtil jwtUtil;

    public  ResponseEntity<?> generateUserTokenResponse(UserDoc user) {
        String token = jwtUtil.signToken(user);
        Map<String , Object> resMap = new HashMap<>();
        resMap.put("user" , UserMapper.mapToUserResponseDTO(user));
        resMap.put("token" , token);
        return ResponseEntity.ok(
                resMap
        );
    }
}
