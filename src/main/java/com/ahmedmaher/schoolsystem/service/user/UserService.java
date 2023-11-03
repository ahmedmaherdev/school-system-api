package com.ahmedmaher.schoolsystem.service.user;

import com.ahmedmaher.schoolsystem.dto.auth.SignupRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserRequestDTO;
import com.ahmedmaher.schoolsystem.dto.user.UserResponseDTO;
import com.ahmedmaher.schoolsystem.entity.UserEntity;
import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.exception.DuplicatedException;
import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> users = this.userRepository.findAll(pageable);
        return users.getContent().stream().map(Mapper::mapUserToUserDTO).collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long userId) throws NotFoundException {
        UserEntity userEntity = this.userRepository.findById(userId).orElse(null);
        if(userEntity == null) throw new NotFoundException("User not found with id: " + userId);
        return Mapper.mapUserToUserDTO(userEntity);
    }

    public UserResponseDTO getUserByUsername(String username) throws NotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(username);
        if(userEntity == null) throw new NotFoundException("User not found with username: " + username);
        return Mapper.mapUserToUserDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO createUser(SignupRequestDTO userDTO){
        UserEntity userEntity =  new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setName(userDTO.getName());
        userEntity.setUsername(userDTO.getUsername());

        String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userEntity.setPassword(hashedPassword);

//        Set<String> userDTORoles = userDTO.getRoles();
//        if( userDTORoles == null) {
//            // set student role as a default if not provided
//            userDTORoles = new HashSet<>();
//            userDTORoles.add("ROLE_STUDENT");
//        }

//        Set<RoleEntity> userRoleEntities = new HashSet<>();
//        for (String role : userDTORoles ) {
//            RoleEntity userRoleEntity = this.roleRepository.getRoleByName(role);
//            if(userRoleEntity != null) userRoleEntities.add(userRoleEntity);
//        }

        userEntity.setRole(UserRole.ROLE_STUDENT);
        userEntity.setPhoto("default.jpg");
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        try {
            this.userRepository.save(userEntity);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedException("Duplicated fields: please, provide another value for username or email.");
        }
        return Mapper.mapUserToUserDTO(userEntity);
    }

    @Transactional
    public UserResponseDTO updateUser(long userId, UserRequestDTO userRequestDTO) throws NotFoundException{
        UserEntity selectedUserEntity = this.userRepository.findById(userId).orElse(null);
        if(selectedUserEntity == null) throw new NotFoundException("User not found with id: " + userId);

        selectedUserEntity.setEmail(userRequestDTO.getEmail());
        selectedUserEntity.setName(userRequestDTO.getName());
        selectedUserEntity.setUsername(userRequestDTO.getUsername());
        selectedUserEntity.setUpdatedAt(LocalDateTime.now());

        try {
            this.userRepository.save(selectedUserEntity);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedException("Duplicated fields: please, provide another value for username or email.");
        }
        return Mapper.mapUserToUserDTO(selectedUserEntity);
    }

    @Transactional
    public void deleteUser(long userId) throws NotFoundException{
        UserEntity deletedUserEntity = this.userRepository.findById(userId).orElse(null);
        if(deletedUserEntity == null) throw new NotFoundException("User not found with id: " + userId);
        this.userRepository.delete(deletedUserEntity);
    }

    public long getAllUsersCount() {
        return this.userRepository.count();
    }
}
