package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponseDTO;
import com.ahmedmaher.schoolsystem.dto.auth.SignupRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.service.user.UserService;
import com.ahmedmaher.schoolsystem.util.APIRoutes;
import com.ahmedmaher.schoolsystem.util.AppFeatures;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(APIRoutes.USER)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")

    @GetMapping("/")
    public ResponseEntity<CustomResponseDTO<?>> getAllUsers(
            @RequestParam(defaultValue = "0" ) int page ,
            @RequestParam(defaultValue = "10" ) int size,
            @RequestParam(defaultValue = "createdAt") String sort) {

        AppFeatures appFeatures = new AppFeatures(sort , size , page);
        List<UserResponseDTO> users = this.userService.getAllUsers(appFeatures.splitPageable());
        long allCount = this.userService.getAllUsersCount();
        int count = users.size();
        Map<String , Object> res = new HashMap<>();
        res.put("users" , users);
        CustomResponseDTO<Map<String , Object>> customResponseDTO = new CustomResponseDTO<>(res , count, allCount);
        return ResponseEntity.ok(customResponseDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPERADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("userId") Long userId){
        UserResponseDTO user = this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getMe")
    public ResponseEntity<UserResponseDTO> getMe(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        UserResponseDTO user = this.userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("'hasRole('ROLE_SUPERADMIN')")

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody SignupRequestDTO userDTO) {
        UserResponseDTO createdUser = this.userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("'hasRole('ROLE_SUPERADMIN')")

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Valid @RequestBody() UserRequestDTO userRequestDTO,
            @PathVariable("userId") long userId
    ) {
        UserResponseDTO updatedUser = this.userService.updateUser(userId , userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("'hasRole('ROLE_SUPERADMIN')")

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser( @PathVariable("userId") long userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
