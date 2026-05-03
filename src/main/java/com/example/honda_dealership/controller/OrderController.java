package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.request.CheckoutRequest;
import com.example.honda_dealership.dto.request.UpdateOrderStatusRequest;
import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.OrderDetailResponse;
import com.example.honda_dealership.dto.response.OrderResponse;
import com.example.honda_dealership.entity.enums.OrderStatus;
import com.example.honda_dealership.security.CustomUserDetails;
import com.example.honda_dealership.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>> checkout(
            @Valid @RequestBody CheckoutRequest request,
            Authentication authentication
    ) {
        Long userId = getUserId(authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(orderService.checkout(userId, request)));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getMyOrders(
            @RequestParam(required = false) OrderStatus status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication
    ) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(ApiResponse.success(orderService.getMyOrders(userId, status, pageable)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getMyOrderDetail(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(ApiResponse.success(orderService.getMyOrderDetail(userId, orderId)));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(ApiResponse.success(orderService.cancelOrder(userId, orderId)));
    }

    private Long getUserId(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser().getId();
    }
}