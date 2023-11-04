package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserUpdateRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.service.user.UserService;
import com.ahmedmaher.schoolsystem.config.EndpointConfig;
import com.ahmedmaher.schoolsystem.util.AppFeatures;
import com.ahmedmaher.schoolsystem.util.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(EndpointConfig.USER)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<CustomResponseDTO<?>> getAllUsers(
            @RequestParam(defaultValue = "0" ) int page ,
            @RequestParam(defaultValue = "10" ) int size,
            @RequestParam(defaultValue = "createdAt") String sort) {

        AppFeatures appFeatures = new AppFeatures(sort , size , page);
        List<UserEntity> userEntities = this.userService.getAll(appFeatures.splitPageable());
        List<UserResponseDTO> users = UserMapper.mapUserEntitiesToUserResponseDTOs(userEntities);
        long allCount = this.userService.getAllUsersCount();
        int count = users.size();

        // handle response
        Map<String , Object> res = new HashMap<>();
        res.put("users" , users);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(
                UserMapper.mapUserEntityToUserResponseDTO(
                        this.userService.getOne(userId)
                )
        );
    }

    @GetMapping("/getMe")
    public ResponseEntity<UserResponseDTO> getMe(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        UserEntity user = this.userService.getByUsername(username);
        return ResponseEntity.ok(
                UserMapper.mapUserEntityToUserResponseDTO(user)
        );
    }

    @PutMapping("/updateMe")
    public ResponseEntity<UserResponseDTO> updateMe(
            @Valid @RequestBody() UserUpdateRequestDTO userUpdateRequestDTO,
            Authentication authentication
    ) {
        String username = (String) authentication.getPrincipal();
        UserEntity userEntity = this.userService.getByUsername(username);
        UserEntity user = UserMapper.mapUserUpdateRequestDTOToUserEntity(userUpdateRequestDTO);
        return ResponseEntity.ok(
                UserMapper.mapUserEntityToUserResponseDTO(
                        this.userService.updateOne(userEntity.getId(), user)
                )
        );
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userDTO) {
        UserEntity userEntity = UserMapper.mapUserRequestDTOToUserEntity(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                UserMapper.mapUserEntityToUserResponseDTO(
                        this.userService.createOne(userEntity)
                )
        );
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Valid @RequestBody() UserUpdateRequestDTO userUpdateRequestDTO,
            @PathVariable("userId") long userId
    ) {
        UserEntity user = UserMapper.mapUserUpdateRequestDTOToUserEntity(userUpdateRequestDTO);
        return ResponseEntity.ok(
                UserMapper.mapUserEntityToUserResponseDTO(
                        this.userService.updateOne(userId , user)
                )
        );
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser( @PathVariable("userId") long userId) {
        this.userService.deleteOne(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUser(
            @RequestParam String s
    ) {
        Pageable pageable = PageRequest.of(0 , 10);
        return ResponseEntity.ok(
                UserMapper.mapUserEntitiesToUserResponseDTOs(
                        this.userService.search(s , pageable)
                )
        );
    }
}
