package com.ahmedmaher.schoolsystem.config;

import com.ahmedmaher.schoolsystem.enums.UserRole;
import com.ahmedmaher.schoolsystem.security.JwtAuthorizationFilter;
import com.ahmedmaher.schoolsystem.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.config.backend.auth.base-uri}")
    private String authBaseURI;
    @Value("${app.config.backend.user.base-uri}")
    private String userBaseURI;

    @Value("${app.config.backend.user.api.load-user-by-id-uri}")
    private String userLoadByIdURI;

    @Value("${app.config.backend.user.api.create-user-uri}")
    private String userCreateURI;

    @Value("${app.config.backend.school.base-uri}")
    private String schoolBaseURI;

    @Value("${app.config.backend.school.api.load-school-by-id-uri}")
    private String schoolLoadByIdURI;

    @Value("${app.config.backend.school.api.create-school-uri}")
    private String schoolCreateURI;

    @Value("${app.config.backend.classroom.base-uri}")
    private String classroomBaseURI;

    @Value("${app.config.backend.classroom.api.load-classroom-by-id-uri}")
    private String classroomLoadByIdURI;

    @Value("${app.config.backend.classroom.api.create-classroom-uri}")
    private String classroomCreateURI;

    @Value("${app.config.backend.enrollment.base-uri}")
    private String enrollmentBaseURI;

    @Value("${app.config.backend.enrollment.api.create-enrollment-uri}")
    private String enrollmentCreateURI;


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
                                .requestMatchers(this.authBaseURI + "/**").permitAll()
                                .requestMatchers("/userPhotos/**").permitAll()

                                // make create, update and delete school to super admin
                                .requestMatchers(HttpMethod.POST , schoolBaseURI + schoolCreateURI).hasAuthority(UserRole.SUPERADMIN.name())
                                .requestMatchers(HttpMethod.PUT , schoolBaseURI + schoolLoadByIdURI).hasAuthority(UserRole.SUPERADMIN.name())
                                .requestMatchers(HttpMethod.DELETE , schoolBaseURI + schoolLoadByIdURI).hasAuthority(UserRole.SUPERADMIN.name())

                                // make create, update and delete classroom to admin
                                .requestMatchers(HttpMethod.POST , classroomBaseURI + classroomCreateURI).hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT , classroomBaseURI + classroomLoadByIdURI).hasAuthority(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE , classroomBaseURI + classroomLoadByIdURI).hasAuthority(UserRole.ADMIN.name())

                                // make create, update and delete user to admin
                                .requestMatchers(HttpMethod.POST , userBaseURI + userCreateURI).hasAuthority(UserRole.SUPERADMIN.name())
                                .requestMatchers(HttpMethod.PUT , userBaseURI + userLoadByIdURI).hasAuthority(UserRole.SUPERADMIN.name())
                                .requestMatchers(HttpMethod.DELETE , userBaseURI + userLoadByIdURI).hasAuthority(UserRole.SUPERADMIN.name())

                                // make create enrollment to admin
                                .requestMatchers(HttpMethod.DELETE , enrollmentBaseURI + enrollmentCreateURI).hasAuthority(UserRole.ADMIN.name())

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
