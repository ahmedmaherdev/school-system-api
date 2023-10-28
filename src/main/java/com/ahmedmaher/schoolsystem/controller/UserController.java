package com.ahmedmaher.schoolsystem.controller;

import com.ahmedmaher.schoolsystem.dto.CustomResponse;
import com.ahmedmaher.schoolsystem.dto.UserDTO;
import com.ahmedmaher.schoolsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = this.userService.getAllUsers();
        long allCount = this.userService.getAllUsersCount();
        int count = users.size();
        CustomResponse<List<UserDTO>> customResponse = new CustomResponse<>(users , count, allCount);
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId){
        UserDTO user = this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = this.userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody() UserDTO userDTO , @PathVariable("userId") long userId) {
        UserDTO updatedUser = this.userService.updateUser(userId , userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser( @PathVariable("userId") long userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
