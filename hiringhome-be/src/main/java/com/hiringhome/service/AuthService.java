package com.hiringhome.service;

import com.hiringhome.config.JwtService;
import com.hiringhome.dto.auth.AuthResponse;
import com.hiringhome.dto.auth.LoginRequest;
import com.hiringhome.dto.auth.RegisterRequest;
import com.hiringhome.entity.User;
import com.hiringhome.entity.enums.UserRole;
import com.hiringhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.debug("Registering new user with username: {}", request.getUsername());
        
        // Validate if passwords match
        if (!request.getPassword().equals(request.getRePassword())) {
            log.debug("Passwords do not match for username: {}", request.getUsername());
            return AuthResponse.builder()
                    .message("Passwords do not match")
                    .build();
        }

        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.debug("Username already exists: {}", request.getUsername());
            return AuthResponse.builder()
                    .message("Username already exists")
                    .build();
        }

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.debug("Email already exists: {}", request.getEmail());
            return AuthResponse.builder()
                    .message("Email already exists")
                    .build();
        }

        // Validate role
        if (request.getRole() == null) {
            request.setRole(UserRole.USER);
        } else if (request.getRole() == UserRole.ADMIN) {
            log.debug("Attempt to register as ADMIN rejected for username: {}", request.getUsername());
            return AuthResponse.builder()
                    .message("Cannot register as ADMIN")
                    .build();
        }

        // Create new user
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFullName(request.getFullName());
        user.setRole(request.getRole());
        user.setEnabled(true);
        user.setLocked(false);

        userRepository.save(user);
        log.debug("User registered successfully: {}", user.getUsername());

        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().toString())
                .message("Registration successful")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        log.debug("Attempting login for username: {}", request.getUsername());
        
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
            log.debug("User found: {}", user.getUsername());

            if (!user.isEnabled()) {
                log.debug("Account is disabled for username: {}", user.getUsername());
                return AuthResponse.builder()
                        .message("Account is disabled")
                        .build();
            }

            if (user.isLocked()) {
                log.debug("Account is locked for username: {}", user.getUsername());
                return AuthResponse.builder()
                        .message("Account is locked")
                        .build();
            }

            var token = jwtService.generateToken(user);
            log.debug("Login successful for username: {}", user.getUsername());
            
            return AuthResponse.builder()
                    .token(token)
                    .username(user.getUsername())
                    .role(user.getRole().toString())
                    .message("Login successful")
                    .build();
                    
        } catch (AuthenticationException e) {
            log.error("Authentication failed for username: {}", request.getUsername(), e);
            return AuthResponse.builder()
                    .message("Invalid username or password")
                    .build();
        }
    }

    public AuthResponse authenticateWithGoogle(String idToken) {
        // TODO: Implement Google authentication
        return null;
    }
}
