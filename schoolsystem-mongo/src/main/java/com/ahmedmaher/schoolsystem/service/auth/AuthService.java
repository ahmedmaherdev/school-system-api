package com.ahmedmaher.schoolsystem.service.auth;

import com.ahmedmaher.schoolsystem.document.UserDocument;

public interface AuthService {
    UserDocument registerUser(UserDocument userDocument);
    UserDocument loginUser(UserDocument userDocument);
    void forgetPassword(UserDocument userDocument);
    UserDocument resetPassword(UserDocument userDocument);
    UserDocument updatePassword(UserDocument userDocument, String newPassword);
}
