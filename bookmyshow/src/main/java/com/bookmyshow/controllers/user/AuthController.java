package com.bookmyshow.controllers.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.dtos.user.AuthResponse;
import com.bookmyshow.dtos.user.LogInRequest;
import com.bookmyshow.dtos.user.SignUpRequest;
import com.bookmyshow.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse register(@RequestBody SignUpRequest signUpRequest) {
        return authService.register(signUpRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LogInRequest logInRequest) {
        return authService.login(logInRequest);
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshAccessToken(@RequestBody String refreshToken) {
        return authService.refreshAccessToken(refreshToken);
    }

}
