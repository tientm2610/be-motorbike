package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.response.BrandResponse;
import com.example.honda_dealership.dto.response.CategoryResponse;
import com.example.honda_dealership.dto.response.MotorcycleResponse;
import com.example.honda_dealership.dto.response.VariantImageResponse;
import com.example.honda_dealership.dto.response.VariantResponse;
import com.example.honda_dealership.entity.Brand;
import com.example.honda_dealership.entity.Category;
import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import com.example.honda_dealership.exception.ResourceNotFoundException;
import com.example.honda_dealership.mapper.ProductMapper;
import com.example.honda_dealership.repository.BrandRepository;
import com.example.honda_dealership.repository.CategoryRepository;
import com.example.honda_dealership.repository.MotorcycleRepository;
import com.example.honda_dealership.repository.MotorcycleVariantRepository;
import com.example.honda_dealership.repository.VariantImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleVariantRepository variantRepository;
    private final VariantImageRepository variantImageRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<MotorcycleResponse> getAllMotorcycles(
            Long brandId,
            Long categoryId,
            MotorcycleStatus status,
            String keyword,
            Pageable pageable
    ) {
        Specification<Motorcycle> spec = (root, query, cb) -> null;

        if (brandId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("brand").get("id"), brandId));
        }

        if (categoryId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        if (keyword != null && !keyword.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("code")), "%" + keyword.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("description")), "%" + keyword.toLowerCase() + "%")
                    ));
        }

        Page<Motorcycle> motorcycles = motorcycleRepository.findAll(spec, pageable);
        return motorcycles.map(productMapper::toMotorcycleSummaryResponse);
    }

    @Transactional(readOnly = true)
    public MotorcycleResponse getMotorcycleById(Long id) {
        Motorcycle motorcycle = motorcycleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorcycle not found with id: " + id));
        return productMapper.toMotorcycleResponse(motorcycle);
    }

    @Transactional(readOnly = true)
    public MotorcycleResponse getMotorcycleBySlug(String slug) {
        Motorcycle motorcycle = motorcycleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Motorcycle not found with slug: " + slug));
        return productMapper.toMotorcycleResponse(motorcycle);
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(productMapper::toBrandResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(productMapper::toCategoryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MotorcycleResponse> searchMotorcycles(String keyword) {
        List<Motorcycle> motorcycles = motorcycleRepository.searchByKeyword(keyword, MotorcycleStatus.ACTIVE);
        return motorcycles.stream()
                .map(productMapper::toMotorcycleSummaryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<VariantResponse> getAllVariants(Pageable pageable) {
        return variantRepository.findAll(pageable)
                .map(productMapper::toVariantResponse);
    }

    @Transactional(readOnly = true)
    public VariantResponse getVariantById(Long id) {
        MotorcycleVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + id));
        return productMapper.toVariantResponse(variant);
    }

    @Transactional(readOnly = true)
    public List<VariantResponse> getVariantsByMotorcycleId(Long motorcycleId) {
        if (!motorcycleRepository.existsById(motorcycleId)) {
            throw new ResourceNotFoundException("Motorcycle not found with id: " + motorcycleId);
        }
        return variantRepository.findByMotorcycleId(motorcycleId).stream()
                .map(productMapper::toVariantResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VariantImageResponse> getImagesByVariantId(Long variantId) {
        if (!variantRepository.existsById(variantId)) {
            throw new ResourceNotFoundException("Variant not found with id: " + variantId);
        }
        return variantImageRepository.findByVariantIdOrderBySortOrderAsc(variantId).stream()
                .map(productMapper::toVariantImageResponse)
                .toList();
    }
}