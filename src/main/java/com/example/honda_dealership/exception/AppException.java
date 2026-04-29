package com.example.honda_dealership.exception;

import com.example.honda_dealership.enums.ResponseCode;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ResponseCode responseCode;

    public AppException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public AppException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
}