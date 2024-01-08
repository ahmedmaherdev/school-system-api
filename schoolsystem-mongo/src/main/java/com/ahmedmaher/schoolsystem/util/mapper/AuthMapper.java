package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.auth.*;
import com.ahmedmaher.schoolsystem.document.UserDocument;

public class AuthMapper {

    public static UserDocument mapToUserDocument(SignupReqDTO signupReqDTO){
        return UserDocument.builder()
                .name(signupReqDTO.getName())
                .email(signupReqDTO.getEmail())
                .username(signupReqDTO.getUsername())
                .password(signupReqDTO.getPassword())
                .build();
    }

    public static UserDocument mapToUserDocument(ForgetPasswordReqDTO forgetPasswordReqDTO){
        return UserDocument.builder()
                .email(forgetPasswordReqDTO.getEmail())
                .build();
    }

    public static UserDocument mapToUserDocument(ResetPasswordReqDTO resetPasswordReqDTO){
        return UserDocument.builder()
                .password(resetPasswordReqDTO.getPassword())
                .build();
    }

    public static UserDocument mapToUserDocument(UpdatePasswordReqDTO updatePasswordReqDTO){
        return UserDocument.builder()
                .password(updatePasswordReqDTO.getPassword())
                .build();
    }

    public static UserDocument mapToUserDocument(LoginReqDTO loginReqDTO){
        return UserDocument.builder()
                .username(loginReqDTO.getUsername())
                .password(loginReqDTO.getPassword())
                .build();
    }

}
