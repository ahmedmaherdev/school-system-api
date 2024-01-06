package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.auth.*;
import com.ahmedmaher.schoolsystem.document.UserDocument;

public class AuthMapper {

    public static UserDocument mapToUserDocument(SignupRequestDTO signupRequestDTO){
        UserDocument userEntity = new UserDocument();
        userEntity.setName(signupRequestDTO.getName());
        userEntity.setEmail(signupRequestDTO.getEmail());
        userEntity.setUsername(signupRequestDTO.getUsername());
        userEntity.setPassword(signupRequestDTO.getPassword());
        return userEntity;
    }

    public static UserDocument mapToUserDocument(ForgetPasswordRequestDTO forgetPasswordRequestDTO){
        UserDocument userDocument = new UserDocument();
        userDocument.setEmail(forgetPasswordRequestDTO.getEmail());
        return userDocument;
    }

    public static UserDocument mapToUserDocument(ResetPasswordRequestDTO resetPasswordRequestDTO){
        UserDocument userDocument = new UserDocument();
        userDocument.setPassword(resetPasswordRequestDTO.getPassword());
        return userDocument;
    }

    public static UserDocument mapToUserDocument(UpdatePasswordRequestDTO updatePasswordRequestDTO){
        UserDocument userDocument = new UserDocument();
        userDocument.setPassword(updatePasswordRequestDTO.getPassword());
        return userDocument;
    }

    public static UserDocument mapToUserDocument(LoginRequestDTO loginRequestDTO){
        UserDocument userEntity = new UserDocument();
        userEntity.setUsername(loginRequestDTO.getUsername());
        userEntity.setPassword(loginRequestDTO.getPassword());
        return userEntity;
    }

}
