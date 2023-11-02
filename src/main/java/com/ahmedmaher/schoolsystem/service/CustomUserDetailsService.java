package com.ahmedmaher.schoolsystem.service;

import com.ahmedmaher.schoolsystem.repository.UserRepository;
import com.ahmedmaher.schoolsystem.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.ahmedmaher.schoolsystem.model.User user = this.userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("Incorrect username and password");
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Mapper.mapRolesSetToGrantedAuthority(user.getRoles()))
                .build();
    }
}
