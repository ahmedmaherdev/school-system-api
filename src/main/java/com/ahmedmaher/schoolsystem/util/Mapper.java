package com.ahmedmaher.schoolsystem.util;

import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.model.Role;
import com.ahmedmaher.schoolsystem.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {
    public static UserDTO mapUserToUserDTO(User user){
        return new UserDTO(user.getId(),
                user.getName(),
                user.getUsername() ,
                user.getEmail() ,
                Mapper.mapUserRoles(user.getRoles()),
                user.getCreatedAt() ,
                user.getUpdatedAt()
        );
    }

    public static Set<String> mapUserRoles(Set<Role> roles) {
        return roles.stream().map(userRole -> userRole.getName()).collect(Collectors.toSet());
    }
}
