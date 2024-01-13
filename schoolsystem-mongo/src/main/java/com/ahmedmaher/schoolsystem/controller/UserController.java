package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserReqDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserUpdateReqDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResDTO;
import com.ahmedmaher.schoolsystem.document.UserDoc;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.service.enrollment.EnrollmentService;
import com.ahmedmaher.schoolsystem.service.user.UserService;
import com.ahmedmaher.schoolsystem.util.AppFeaturesUtil;
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
    private final EnrollmentService enrollmentService;

    @Autowired
    public UserController(UserService userService, EnrollmentService enrollmentService) {
        this.userService = userService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("${app.config.backend.user.api.load-all-users-uri}")
    public ResponseEntity<CustomResDTO<?>> getAllUsers(
            @RequestParam(defaultValue = "0" ) int page ,
            @RequestParam(defaultValue = "10" ) int size,
            @RequestParam(defaultValue = "createdAt") String sort
    ) {

        AppFeaturesUtil appFeaturesUtil = new AppFeaturesUtil(sort , size , page);
        List<UserDoc> userEntities = userService.getAll(appFeaturesUtil.splitPageable());
        List<UserResDTO> users = UserMapper.mapToUserResponseDTOs(userEntities);
        long allCount = userService.getAllUsersCount();
        int count = users.size();

        // handle response
        Map<String , Object> res = new HashMap<>();
        res.put("users" , users);
        CustomResDTO<Map<String , Object>> customResDTO = new CustomResDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResDTO);
    }

    @GetMapping("${app.config.backend.user.api.load-user-by-id-uri}")
    public ResponseEntity<UserResDTO> getUser(@PathVariable("userId") String userId){
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(
                        userService.getOne(userId)
                )
        );
    }

    @GetMapping("${app.config.backend.user.api.load-me-uri}")
    public ResponseEntity<UserResDTO> getMe(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        UserDoc user = userService.getByUsername(username);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(user)
        );
    }

    @PutMapping("${app.config.backend.user.api.update-me-uri}")
    public ResponseEntity<UserResDTO> updateMe(
            @Valid @RequestBody() UserUpdateReqDTO userUpdateReqDTO,
            Authentication authentication
    ) {
        String username = (String) authentication.getPrincipal();
        UserDoc userEntity = userService.getByUsername(username);
        UserDoc user = UserMapper.mapToUserDocument(userUpdateReqDTO);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(
                        userService.updateOne(userEntity.getId(), user)
                )
        );
    }

    @PutMapping("${app.config.backend.user.api.update-my-photo-uri}")
    public ResponseEntity<UserResDTO> updateMyPhoto(
            @RequestPart("photo") MultipartFile photoFile,
            Authentication authentication
    ) throws Exception {
        String username = (String) authentication.getPrincipal();
        UserDoc userEntity = userService.getByUsername(username);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(
                        userService.updateUserPhoto(userEntity, photoFile)
                )
        );
    }

    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @PostMapping("${app.config.backend.user.api.create-user-uri}")
    public ResponseEntity<UserResDTO> createUser(@Valid @RequestBody UserReqDTO userDTO) {
        UserDoc userEntity = UserMapper.mapToUserDocument(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                UserMapper.mapToUserResponseDTO(
                        userService.createOne(userEntity)
                )
        );
    }

    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @PutMapping("${app.config.backend.user.api.load-user-by-id-uri}")
    public ResponseEntity<UserResDTO> updateUser(
            @Valid @RequestBody() UserUpdateReqDTO userUpdateReqDTO,
            @PathVariable("userId") String userId
    ) {
        UserDoc user = UserMapper.mapToUserDocument(userUpdateReqDTO);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTO(
                        userService.updateOne(userId , user)
                )
        );
    }

    @RolesAllowed( UserRole.Names.SUPERADMIN)
    @DeleteMapping("${app.config.backend.user.api.load-user-by-id-uri}")
    public ResponseEntity<?> deleteUser( @PathVariable("userId") String userId) {
        userService.deleteOne(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("${app.config.backend.user.api.load-search-users-uri}")
    public ResponseEntity<List<UserResDTO>> searchUser(
            @RequestParam String s
    ) {
        Pageable pageable = PageRequest.of(0 , 10);
        return ResponseEntity.ok(
                UserMapper.mapToUserResponseDTOs(
                        userService.search(s , pageable)
                )
        );
    }

}
