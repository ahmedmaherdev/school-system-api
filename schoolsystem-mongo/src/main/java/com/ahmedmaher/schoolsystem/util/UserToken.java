package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;

import java.util.HashMap;
import java.util.Map;

public class UserToken {

    public static Map<String , Object> generateUserTokenResponse(UserResponseDTO user , String token) {
        Map<String , Object> res = new HashMap<>();
        res.put("user" , user);
        res.put("token" , token);
        return res;
    }
}
