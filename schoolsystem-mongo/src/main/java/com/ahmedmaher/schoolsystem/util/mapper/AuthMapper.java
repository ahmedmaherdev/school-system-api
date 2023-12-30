package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.auth.LoginRequestDTO;
import com.ahmedmaher.schoolsystem.dto.auth.SignupRequestDTO;
import com.ahmedmaher.schoolsystem.document.UserDocument;

public class AuthMapper {

    public static UserDocument mapToUserEntity(SignupRequestDTO signupRequestDTO){
        UserDocument userEntity = new UserDocument();
        userEntity.setName(signupRequestDTO.getName());
        userEntity.setEmail(signupRequestDTO.getEmail());
        userEntity.setUsername(signupRequestDTO.getUsername());
        userEntity.setPassword(signupRequestDTO.getPassword());
        return userEntity;
    }

    public static UserDocument mapToUserEntity(LoginRequestDTO loginRequestDTO){
        UserDocument userEntity = new UserDocument();
        userEntity.setUsername(loginRequestDTO.getUsername());
        userEntity.setPassword(loginRequestDTO.getPassword());
        return userEntity;
    }

}
