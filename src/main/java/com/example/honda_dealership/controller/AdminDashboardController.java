package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.DashboardSummaryResponse;
import com.example.honda_dealership.dto.response.OrderStatusCountResponse;
import com.example.honda_dealership.dto.response.RevenueResponse;
import com.example.honda_dealership.dto.response.TopProductResponse;
import com.example.honda_dealership.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getSummary() {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getSummary()));
    }

    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<List<RevenueResponse>>> getRevenue(
            @RequestParam(required = false, defaultValue = "7days") String range
    ) {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getRevenue(range)));
    }

    @GetMapping("/top-products")
    public ResponseEntity<ApiResponse<List<TopProductResponse>>> getTopProducts() {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getTopProducts()));
    }

    @GetMapping("/order-status")
    public ResponseEntity<ApiResponse<OrderStatusCountResponse>> getOrderStatus() {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getOrderStatusCounts()));
    }
}