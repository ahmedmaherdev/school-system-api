package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserUpdateRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.service.user.UserService;
import com.ahmedmaher.schoolsystem.util.AppFeatures;
import com.ahmedmaher.schoolsystem.util.mapper.ClassroomMapper;
import com.ahmedmaher.schoolsystem.util.mapper.UserMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${app.config.backend.user.base-uri}")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("${app.config.backend.user.api.load-all-users-uri}")
    public ResponseEntity<CustomResponseDTO<?>> getAllUsers(
            @RequestParam(defaultValue = "0" ) int page ,
            @RequestParam(defaultValue = "10" ) int size,
            @RequestParam(defaultValue = "createdAt") String sort
    ) {

        AppFeatures appFeatures = new AppFeatures(sort , size , page);
        List<UserEntity> userEntities = userService.getAll(appFeatures.splitPageable());
        List<UserResponseDTO> users = UserMapper.mapToUserResponseDTOs(userEntities);
        long allCount = userService.getAllUsersCount();
        int count = users.size();

        // handle response
        Map<String , Object> res = new HashMap<>();
        res.put("users" , users);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @GetMapping("${app.config.backend.user.api.load-user-by-id-uri}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(
                        userService.getOne(userId)
                )
        );
    }

    @GetMapping("${app.config.backend.user.api.load-me-uri}")
    public ResponseEntity<UserResponseDTO> getMe(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        UserEntity user = userService.getByUsername(username);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(user)
        );
    }

    @PutMapping("${app.config.backend.user.api.update-me-uri}")
    public ResponseEntity<UserResponseDTO> updateMe(
            @Valid @RequestBody() UserUpdateRequestDTO userUpdateRequestDTO,
            Authentication authentication
    ) {
        String username = (String) authentication.getPrincipal();
        UserEntity userEntity = userService.getByUsername(username);
        UserEntity user = UserMapper.mapToUserEntity(userUpdateRequestDTO);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(
                        userService.updateOne(userEntity.getId(), user)
                )
        );
    }

    @PutMapping("${app.config.backend.user.api.update-my-photo-uri}")
    public ResponseEntity<UserResponseDTO> updateMyPhoto(
            @Valid @RequestParam("photo") MultipartFile photoFile,
            Authentication authentication
    ) throws Exception {
        String username = (String) authentication.getPrincipal();
        UserEntity userEntity = userService.getByUsername(username);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(
                        userService.updateUserPhoto(userEntity, photoFile)
                )
        );
    }

    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @PostMapping("${app.config.backend.user.api.create-user-uri}")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userDTO) {
        UserEntity userEntity = UserMapper.mapToUserEntity(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                UserMapper.mapToUserResponseDTO(
                        userService.createOne(userEntity)
                )
        );
    }

    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @PutMapping("${app.config.backend.user.api.load-user-by-id-uri}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Valid @RequestBody() UserUpdateRequestDTO userUpdateRequestDTO,
            @PathVariable("userId") long userId
    ) {
        UserEntity user = UserMapper.mapToUserEntity(userUpdateRequestDTO);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(
                        userService.updateOne(userId , user)
                )
        );
    }

    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @DeleteMapping("${app.config.backend.user.api.load-user-by-id-uri}")
    public ResponseEntity<?> deleteUser( @PathVariable("userId") long userId) {
        userService.deleteOne(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("${app.config.backend.user.api.load-search-users-uri}")
    public ResponseEntity<List<UserResponseDTO>> searchUser(
            @RequestParam String s
    ) {
        Pageable pageable = PageRequest.of(0 , 10);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTOs(
                        userService.search(s , pageable)
                )
        );
    }

    @GetMapping("${app.config.backend.user.api.load-all-enrollments-by-user-uri}")
    public ResponseEntity<?> getAllStudentEnrollments(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(
                ClassroomMapper.mapToClassroomResponseDTOs(
                        userService.getStudentEnrollments(userId)
                )
        );
    }
    @PostMapping("${app.config.backend.user.api.create-user-enrollment-uri}")
    public ResponseEntity<?> createStudentEnrollment(
            @PathVariable("userId") long userId,
            @PathVariable("classroomId") long classroomId
    ) {
        userService.createStudentEnrollment(userId , classroomId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("${app.config.backend.user.api.remove-user-enrollment-uri}")
    public ResponseEntity<?> deleteStudentEnrollment(
            @PathVariable("userId") long userId,
            @PathVariable("classroomId") long classroomId
    ) {
        userService.deleteStudentEnrollment(userId , classroomId);
        return ResponseEntity.noContent().build();
    }
}
