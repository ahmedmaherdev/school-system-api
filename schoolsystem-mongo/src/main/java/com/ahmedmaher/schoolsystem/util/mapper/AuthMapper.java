package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.auth.*;
import com.ahmedmaher.schoolsystem.document.UserDoc;

public class AuthMapper {

    public static UserDoc mapToUserDocument(SignupReqDTO signupReqDTO){
        return UserDoc.builder()
                .name(signupReqDTO.getName())
                .email(signupReqDTO.getEmail())
                .username(signupReqDTO.getUsername())
                .password(signupReqDTO.getPassword())
                .build();
    }

    public static UserDoc mapToUserDocument(ForgetPasswordReqDTO forgetPasswordReqDTO){
        return UserDoc.builder()
                .email(forgetPasswordReqDTO.getEmail())
                .build();
    }

    public static UserDoc mapToUserDocument(ResetPasswordReqDTO resetPasswordReqDTO){
        return UserDoc.builder()
                .password(resetPasswordReqDTO.getPassword())
                .build();
    }

    public static UserDoc mapToUserDocument(UpdatePasswordReqDTO updatePasswordReqDTO){
        return UserDoc.builder()
                .password(updatePasswordReqDTO.getOldPassword())
                .build();
    }

    public static UserDoc mapToUserDocument(LoginReqDTO loginReqDTO){
        return UserDoc.builder()
                .username(loginReqDTO.getUsername())
                .password(loginReqDTO.getPassword())
                .build();
    }

}
