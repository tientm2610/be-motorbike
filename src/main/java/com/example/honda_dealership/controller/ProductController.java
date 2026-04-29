package com.example.honda_dealership.controller;

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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/motorcycles")
    public ResponseEntity<Page<MotorcycleResponse>> getAllMotorcycles(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) MotorcycleStatus status,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(productService.getAllMotorcycles(
                brandId, categoryId, minPrice, maxPrice, status, keyword, pageable));
    }

    @GetMapping("/motorcycles/{id}")
    public ResponseEntity<MotorcycleResponse> getMotorcycleById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getMotorcycleById(id));
    }

    @GetMapping("/motorcycles/slug/{slug}")
    public ResponseEntity<MotorcycleResponse> getMotorcycleBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(productService.getMotorcycleBySlug(slug));
    }

    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        return ResponseEntity.ok(productService.getAllBrands());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(productService.getAllCategories());
    }
}