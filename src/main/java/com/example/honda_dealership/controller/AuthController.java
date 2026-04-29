package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.request.LoginRequest;
import com.example.honda_dealership.dto.request.RegisterRequest;
import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.AuthResponse;
import com.example.honda_dealership.dto.response.UserProfileResponse;
import com.example.honda_dealership.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(request)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(authService.getProfile(authentication.getName())));
    }
}