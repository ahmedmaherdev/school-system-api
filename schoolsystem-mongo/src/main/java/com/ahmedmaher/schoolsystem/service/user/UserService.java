package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.document.UserDoc;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.base.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends BaseService<UserDoc> {
    UserDoc getByUsername(String username) throws NotFoundException;
    UserDoc getByEmail(String email) throws NotFoundException;
    UserDoc getByPasswordResetToken(String passwordResetToken);
    UserDoc updateUserPhoto(UserDoc user, MultipartFile photoFile) throws Exception;
    UserDoc saveUser(UserDoc user);
    List<UserDoc> search(String name, Pageable pageable);
    long getAllUsersCount();
}
