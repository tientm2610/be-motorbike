package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.OrderDetailResponse;
import com.example.honda_dealership.dto.response.OrderResponse;
import com.example.honda_dealership.entity.enums.OrderStatus;
import com.example.honda_dealership.security.CustomUserDetails;
import com.example.honda_dealership.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
public class StaffOrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getOrders(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) OrderStatus status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getStaffOrders(search, status, pageable)));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrderDetail(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderDetail(orderId)));
    }

    @PutMapping("/orders/{orderId}/confirm")
    public ResponseEntity<ApiResponse<OrderResponse>> confirmOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        Long staffId = getStaffId(authentication);
        return ResponseEntity.ok(ApiResponse.success(orderService.confirmOrder(staffId, orderId)));
    }

    @PutMapping("/orders/{orderId}/preparing")
    public ResponseEntity<ApiResponse<OrderResponse>> preparingOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        Long staffId = getStaffId(authentication);
        return ResponseEntity.ok(ApiResponse.success(orderService.preparingOrder(staffId, orderId)));
    }

    @PutMapping("/orders/{orderId}/shipping")
    public ResponseEntity<ApiResponse<OrderResponse>> shippingOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        Long staffId = getStaffId(authentication);
        return ResponseEntity.ok(ApiResponse.success(orderService.shippingOrder(staffId, orderId)));
    }

    @PutMapping("/orders/{orderId}/delivered")
    public ResponseEntity<ApiResponse<OrderResponse>> deliveredOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        Long staffId = getStaffId(authentication);
        return ResponseEntity.ok(ApiResponse.success(orderService.deliveredOrder(staffId, orderId)));
    }

    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        Long staffId = getStaffId(authentication);
        return ResponseEntity.ok(ApiResponse.success(orderService.staffCancelOrder(staffId, orderId)));
    }

    private Long getStaffId(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser().getId();
    }
}