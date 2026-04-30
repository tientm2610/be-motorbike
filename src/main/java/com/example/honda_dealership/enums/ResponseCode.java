package com.example.honda_dealership.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(1000, "Success"),
    UNAUTHORIZED(1001, "Unauthorized"),
    INVALID_CREDENTIALS(1002, "Invalid email or password"),
    USER_NOT_FOUND(1003, "User not found"),
    EMAIL_ALREADY_EXISTS(1004, "Email already exists"),
    VALIDATION_ERROR(1005, "Validation error"),
    ACCESS_DENIED(1006, "Access denied"),
    RESOURCE_NOT_FOUND(1007, "Resource not found"),
    BAD_REQUEST(1008, "Bad request"),
    TOKEN_EXPIRED(1009, "Token expired"),
    INVALID_TOKEN(1010, "Invalid token"),
    REFRESH_TOKEN_REVOKED(1011, "Refresh token has been revoked"),
    LOGOUT_SUCCESS(1012, "Logout successful"),
    INTERNAL_SERVER_ERROR(9999, "Internal server error");

    private final int code;
    private final String message;
}