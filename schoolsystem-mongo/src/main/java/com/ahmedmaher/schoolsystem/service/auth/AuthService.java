package com.ahmedmaher.schoolsystem.service.auth;

import com.ahmedmaher.schoolsystem.document.UserDoc;

public interface AuthService {
    UserDoc registerUser(UserDoc userDoc);
    UserDoc loginUser(UserDoc userDoc);
    void forgetPassword(UserDoc userDoc);
    UserDoc resetPassword(UserDoc userDoc);
    UserDoc updatePassword(UserDoc userDoc, String newPassword);
}
