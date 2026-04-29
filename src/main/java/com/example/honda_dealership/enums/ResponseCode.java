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
    INTERNAL_SERVER_ERROR(9999, "Internal server error");

    private final int code;
    private final String message;
}