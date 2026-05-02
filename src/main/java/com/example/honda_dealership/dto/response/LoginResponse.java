package com.example.honda_dealership.dto.response;

import com.example.honda_dealership.entity.enums.UserRole;
import com.example.honda_dealership.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private UserStatus status;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private LocalDateTime createdAt;
    private Integer cartItemCount;
}