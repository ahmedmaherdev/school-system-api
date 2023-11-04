package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.user.UserRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserUpdateRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponseDTO mapUserEntityToUserResponseDTO(UserEntity userEntity){
        return new UserResponseDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                mapUserRolesToStringSet(userEntity.getRoles()),
                userEntity.getPhoto(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    public static UserEntity mapUserUpdateRequestDTOToUserEntity(UserUpdateRequestDTO userUpdateRequestDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userUpdateRequestDTO.getName());
        userEntity.setEmail(userUpdateRequestDTO.getEmail());
        userEntity.setUsername(userUpdateRequestDTO.getUsername());
        userEntity.setName(userEntity.getName());
        return userEntity;
    }

    public static UserEntity mapUserRequestDTOToUserEntity(UserRequestDTO userRequestDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequestDTO.getName());
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setUsername(userRequestDTO.getUsername());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity.setRoles(
                mapStringSetToUserRoles(
                        userRequestDTO.getRoles()
                )
        );
        return userEntity;
    }

    public static List<UserResponseDTO> mapUserEntitiesToUserResponseDTOs(List<UserEntity> UserEntities){
        return UserEntities.stream().map(UserMapper::mapUserEntityToUserResponseDTO).collect(Collectors.toList());
    }

    public static List<SimpleGrantedAuthority> mapRolesToGrantedAuthority(Set<UserRole> userRoles){
        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.name())).collect(Collectors.toList());
    }

    public static Set<String> mapUserRolesToStringSet(Set<UserRole> userRoles) {
        return userRoles.stream().map(Enum::name).collect(Collectors.toSet());
    }

    public static Set<UserRole> mapStringSetToUserRoles(Set<String> roles) {
        Set<UserRole> userRoles = new HashSet<>();
        for (String role: roles) {
            try{
                userRoles.add(UserRole.valueOf(role));
            }catch (IllegalArgumentException ignored){}
        }
        return userRoles;
    }
}
