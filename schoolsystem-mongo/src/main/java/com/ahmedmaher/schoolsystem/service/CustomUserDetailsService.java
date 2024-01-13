package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.document.UserDoc;
import com.ahmedmaher.schoolsystem.repository.UserRepo;
import com.ahmedmaher.schoolsystem.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDoc user = userRepo.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("Incorrect username and password");
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(UserMapper.mapToGrantedAuthority(user.getRoles()))
                .build();
    }
}
