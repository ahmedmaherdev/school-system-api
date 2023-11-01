package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class UserToken {

    public static Map<String , Object> generateUserTokenResponse(UserDTO user , String token) {
        Map<String , Object> res = new HashMap<>();
        res.put("user" , user);
        res.put("token" , token);
        return res;
    }
}
