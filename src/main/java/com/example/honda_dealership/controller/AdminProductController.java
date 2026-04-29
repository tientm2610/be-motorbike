package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.request.*;
import com.example.honda_dealership.dto.response.BrandResponse;
import com.example.honda_dealership.dto.response.CategoryResponse;
import com.example.honda_dealership.dto.response.MotorcycleResponse;
import com.example.honda_dealership.dto.response.VariantResponse;
import com.example.honda_dealership.entity.enums.VariantStatus;
import com.example.honda_dealership.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/motorcycles")
    public ResponseEntity<MotorcycleResponse> createMotorcycle(@Valid @RequestBody CreateMotorcycleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminProductService.createMotorcycle(request));
    }

    @PutMapping("/motorcycles/{id}")
    public ResponseEntity<MotorcycleResponse> updateMotorcycle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMotorcycleRequest request
    ) {
        return ResponseEntity.ok(adminProductService.updateMotorcycle(id, request));
    }

    @DeleteMapping("/motorcycles/{id}")
    public ResponseEntity<Void> deleteMotorcycle(@PathVariable Long id) {
        adminProductService.deleteMotorcycle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/brands")
    public ResponseEntity<BrandResponse> createBrand(@Valid @RequestBody CreateBrandRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                adminProductService.createBrand(request.getName(), request.getLogoUrl(), request.getDescription())
        );
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                adminProductService.createCategory(request.getName(), request.getSlug(), request.getDescription())
        );
    }

    @PostMapping("/variants")
    public ResponseEntity<VariantResponse> createVariant(@Valid @RequestBody CreateVariantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminProductService.createVariant(request));
    }

    @PutMapping("/variants/{id}")
    public ResponseEntity<VariantResponse> updateVariant(
            @PathVariable Long id,
            @RequestParam(required = false) Integer stockQuantity,
            @RequestParam(required = false) VariantStatus status
    ) {
        return ResponseEntity.ok(adminProductService.updateVariant(id, stockQuantity, status));
    }

    @DeleteMapping("/variants/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        adminProductService.deleteVariant(id);
        return ResponseEntity.noContent().build();
    }
}