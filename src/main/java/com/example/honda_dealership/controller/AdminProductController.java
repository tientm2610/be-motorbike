package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.request.*;
import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.BrandResponse;
import com.example.honda_dealership.dto.response.CategoryResponse;
import com.example.honda_dealership.dto.response.MotorcycleResponse;
import com.example.honda_dealership.dto.response.VariantImageResponse;
import com.example.honda_dealership.dto.response.VariantResponse;
import com.example.honda_dealership.entity.enums.VariantStatus;
import com.example.honda_dealership.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/motorcycles")
    public ResponseEntity<ApiResponse<MotorcycleResponse>> createMotorcycle(@Valid @RequestBody CreateMotorcycleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(adminProductService.createMotorcycle(request)));
    }

    @PutMapping("/motorcycles/{id}")
    public ResponseEntity<ApiResponse<MotorcycleResponse>> updateMotorcycle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMotorcycleRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(adminProductService.updateMotorcycle(id, request)));
    }

    @DeleteMapping("/motorcycles/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMotorcycle(@PathVariable Long id) {
        adminProductService.deleteMotorcycle(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/brands")
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(@Valid @RequestBody CreateBrandRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                adminProductService.createBrand(request.getName(), request.getLogoUrl(), request.getDescription())
        ));
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                adminProductService.createCategory(request.getName(), request.getSlug(), request.getDescription())
        ));
    }

    @PostMapping("/variants")
    public ResponseEntity<ApiResponse<VariantResponse>> createVariant(@Valid @RequestBody CreateVariantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(adminProductService.createVariant(request)));
    }

    @PutMapping("/variants/{id}")
    public ResponseEntity<ApiResponse<VariantResponse>> updateVariant(
            @PathVariable Long id,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer stockQuantity,
            @RequestParam(required = false) VariantStatus status
    ) {
        return ResponseEntity.ok(ApiResponse.success(adminProductService.updateVariant(id, price, stockQuantity, status)));
    }

    @DeleteMapping("/variants/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVariant(@PathVariable Long id) {
        adminProductService.deleteVariant(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/variant-images")
    public ResponseEntity<ApiResponse<VariantImageResponse>> addVariantImage(@Valid @RequestBody CreateVariantImageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(adminProductService.addVariantImage(request)));
    }

    @DeleteMapping("/variant-images/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVariantImage(@PathVariable Long id) {
        adminProductService.deleteVariantImage(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}