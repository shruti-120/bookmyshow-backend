package com.bookmyshow.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookmyshow.dtos.user.AuthResponse;
import com.bookmyshow.dtos.user.LogInRequest;
import com.bookmyshow.dtos.user.SignUpRequest;
import com.bookmyshow.enums.Role;
import com.bookmyshow.models.User;
import com.bookmyshow.repositories.UserRepository;
import com.bookmyshow.utils.exceptionHandlers.AuthenticationFailedException;
import com.bookmyshow.utils.exceptionHandlers.InvalidTokenException;
import com.bookmyshow.utils.exceptionHandlers.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    
    public AuthResponse register(SignUpRequest signUpRequest) {
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            log.warn("Registration failed: email already exists={}", signUpRequest.getEmail());
            throw new IllegalStateException("User already exists");
        }

        User user = User.builder()
            .email(signUpRequest.getEmail())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .name(signUpRequest.getName())
            .phone(signUpRequest.getPhone())
            .role(Role.USER)
            .build();
        
        userRepository.save(user);
        log.info("User registered successfully id={}, email={}", user.getId(), user.getEmail());

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthResponse(token, refreshToken);
    }

    public AuthResponse login(LogInRequest logInRequest) {
         User user = userRepository.findByEmail(logInRequest.getEmail())
                     .orElseThrow(() -> {
                        log.warn("Login failed: user not found email={}", logInRequest.getEmail());
                        throw new ResourceNotFoundException("Invalid credentials");
                    });

        if(!passwordEncoder.matches(logInRequest.getPassword(), user.getPassword())) {
            log.warn("Login failed: bad password for email={}", logInRequest.getEmail());
            throw new AuthenticationFailedException("Invalid credentials");

        }

        log.info("Login successful for user id={}", user.getId());
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthResponse(token, refreshToken);
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        String email = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.error("Refresh failed: user not found email={}", email);
                return new ResourceNotFoundException("User not found");
            });

        if (!jwtService.isRefreshTokenValid(refreshToken, user)) {
            log.warn("Refresh failed: invalid refresh token for email={}", email);
            throw new InvalidTokenException("Invalid refresh token");
        }

        log.info("Refresh token successful for user id={}", user.getId());
        String newRefreshAccessToken = jwtService.generateRefreshToken(user);
        return new AuthResponse(newRefreshAccessToken, refreshToken);
    }
} 
   