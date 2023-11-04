package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.service.BaseService;

public interface UserService extends BaseService<UserEntity> {
    UserEntity getByUsername(String username) throws NotFoundException;
    long getAllUsersCount();

}
