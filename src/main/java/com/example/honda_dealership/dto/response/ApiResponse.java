package com.example.honda_dealership.dto.response;

import com.example.honda_dealership.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T result;

    public static <T> ApiResponse<T> success(T result) {
        return ApiResponse.<T>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> of(ResponseCode responseCode, T result) {
        return ApiResponse.<T>builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> of(ResponseCode responseCode) {
        return ApiResponse.<T>builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .result(null)
                .build();
    }
}