package com.ahmedmaher.schoolsystem.config;

import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.security.JwtAuthorizationFilter;
import com.ahmedmaher.schoolsystem.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/userPhotos/**").permitAll()

                                // make create, update and delete school to super admin
                                .requestMatchers(HttpMethod.POST , EndpointConfig.SCHOOL).hasAuthority(UserRole.SUPERADMIN.name())
                                .requestMatchers(HttpMethod.PUT , EndpointConfig.SCHOOL_ID).hasAuthority(UserRole.SUPERADMIN.name())
                                .requestMatchers(HttpMethod.DELETE , EndpointConfig.SCHOOL_ID).hasAuthority(UserRole.SUPERADMIN.name())

                                // make create, update and delete classroom to admin
                                .requestMatchers(HttpMethod.POST , EndpointConfig.CLASSROOM).hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT , EndpointConfig.CLASSROOM_ID).hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE , EndpointConfig.CLASSROOM_ID).hasAuthority(UserRole.ADMIN.name())

                                // make create, update and delete user to admin
                                .requestMatchers(HttpMethod.POST , EndpointConfig.USER).hasAuthority(UserRole.SUPERADMIN.name())
                                .requestMatchers(HttpMethod.PUT , EndpointConfig.USER_ID).hasAuthority(UserRole.SUPERADMIN.name())
                                .requestMatchers(HttpMethod.DELETE , EndpointConfig.USER_ID).hasAuthority(UserRole.SUPERADMIN.name())

                                .anyRequest().authenticated()
                ).sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS
                        )
                ).addFilterBefore(jwtAuthorizationFilter , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
