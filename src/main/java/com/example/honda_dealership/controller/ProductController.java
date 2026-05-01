package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.BrandResponse;
import com.example.honda_dealership.dto.response.CategoryResponse;
import com.example.honda_dealership.dto.response.MotorcycleResponse;
import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import com.example.honda_dealership.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/motorcycles")
    public ResponseEntity<ApiResponse<Page<MotorcycleResponse>>> getAllMotorcycles(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) MotorcycleStatus status,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllMotorcycles(
                brandId, categoryId, status, keyword, pageable)));
    }

    @GetMapping("/motorcycles/{id}")
    public ResponseEntity<ApiResponse<MotorcycleResponse>> getMotorcycleById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(productService.getMotorcycleById(id)));
    }

    @GetMapping("/motorcycles/slug/{slug}")
    public ResponseEntity<ApiResponse<MotorcycleResponse>> getMotorcycleBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(productService.getMotorcycleBySlug(slug)));
    }

    @GetMapping("/motorcycles/search")
    public ResponseEntity<ApiResponse<List<MotorcycleResponse>>> searchMotorcycles(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success(productService.searchMotorcycles(keyword)));
    }

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands() {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllBrands()));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllCategories()));
    }
}