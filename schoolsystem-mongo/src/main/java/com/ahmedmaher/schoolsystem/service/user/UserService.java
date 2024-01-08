package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.base.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends BaseService<UserDocument> {
    UserDocument getByUsername(String username) throws NotFoundException;
    UserDocument getByEmail(String email) throws NotFoundException;
    UserDocument getByPasswordResetToken(String passwordResetToken);
    UserDocument updateUserPhoto(UserDocument user, MultipartFile photoFile) throws Exception;
    UserDocument saveUser(UserDocument user);
    List<UserDocument> search(String name, Pageable pageable);
    long getAllUsersCount();
}
