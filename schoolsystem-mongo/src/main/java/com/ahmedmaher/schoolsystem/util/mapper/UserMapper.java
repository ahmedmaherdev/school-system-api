package com.ahmedmaher.schoolsystem.util.mapper;

import com.ahmedmaher.schoolsystem.dto.user.UserReqDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserUpdateReqDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResDTO;
import com.ahmedmaher.schoolsystem.document.UserDocument;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResDTO mapToUserResponseDTO(UserDocument userEntity){
        return new UserResDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                mapToStringSet(userEntity.getRoles()),
                userEntity.getPhoto(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    public static UserDocument mapToUserDocument(UserUpdateReqDTO userUpdateReqDTO){
        return UserDocument.builder()
                .name(userUpdateReqDTO.getName())
                .email(userUpdateReqDTO.getEmail())
                .username(userUpdateReqDTO.getUsername())
                .build();
    }

    public static UserDocument mapToUserDocument(UserReqDTO userReqDTO){
        return UserDocument.builder()
                .name(userReqDTO.getName())
                .email(userReqDTO.getEmail())
                .username(userReqDTO.getUsername())
                .password(userReqDTO.getPassword())
                .roles(mapToUserRoles(userReqDTO.getRoles()))
                .build();
    }

    public static List<UserResDTO> mapToUserResponseDTOs(List<UserDocument> UserEntities){
        return UserEntities.stream().map(UserMapper::mapToUserResponseDTO).collect(Collectors.toList());
    }

    public static List<SimpleGrantedAuthority> mapToGrantedAuthority(Set<UserRole> userRoles){
        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.name())).collect(Collectors.toList());
    }

    public static Set<String> mapToStringSet(Set<UserRole> userRoles) {
        return userRoles.stream().map(Enum::name).collect(Collectors.toSet());
    }

    public static Set<UserRole> mapToUserRoles(Set<String> roles) {
        Set<UserRole> userRoles = new HashSet<>();
        for (String role: roles) {
            try{
                userRoles.add(UserRole.valueOf(role));
            }catch (IllegalArgumentException ignored){}
        }
        return userRoles;
    }
}
