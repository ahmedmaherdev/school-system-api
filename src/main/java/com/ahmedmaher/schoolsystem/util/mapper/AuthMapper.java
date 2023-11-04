package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.auth.LoginRequestDTO;
import com.ahmedmaher.schoolsystem.dto.auth.SignupRequestDTO;
import com.ahmedmaher.schoolsystem.entity.UserEntity;

public class AuthMapper {

    public static UserEntity mapSignupDTOToUserEntity(SignupRequestDTO signupRequestDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(signupRequestDTO.getName());
        userEntity.setEmail(signupRequestDTO.getEmail());
        userEntity.setUsername(signupRequestDTO.getUsername());
        userEntity.setPassword(signupRequestDTO.getPassword());
        return userEntity;
    }

    public static UserEntity mapLoginDTOToUserEntity(LoginRequestDTO loginRequestDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginRequestDTO.getUsername());
        userEntity.setPassword(loginRequestDTO.getPassword());
        return userEntity;
    }

}
