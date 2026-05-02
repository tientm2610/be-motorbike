package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.response.BrandResponse;
import com.example.honda_dealership.dto.response.CategoryResponse;
import com.example.honda_dealership.dto.response.MotorcycleListResponse;
import com.example.honda_dealership.dto.response.MotorcycleResponse;
import com.example.honda_dealership.dto.response.VariantImageResponse;
import com.example.honda_dealership.dto.response.VariantResponse;
import com.example.honda_dealership.entity.Brand;
import com.example.honda_dealership.entity.Category;
import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.VariantImage;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Page<MotorcycleListResponse> getAllMotorcycles(
            Long brandId,
            Long categoryId,
            MotorcycleStatus status,
            String keyword,
            Pageable pageable
    ) {
        Page<Motorcycle> motorcycles = motorcycleRepository.findAllMotorcyclesWithDetails(
                brandId, categoryId, status, keyword, pageable);

        List<Long> variantIds = motorcycles.getContent().stream()
                .flatMap(m -> m.getVariants().stream())
                .map(MotorcycleVariant::getId)
                .distinct()
                .toList();

        Map<Long, List<VariantImage>> imagesByVariant = variantImageRepository.findByVariantIdIn(variantIds).stream()
                .collect(Collectors.groupingBy(img -> img.getVariant().getId()));

        return motorcycles.map(motorcycle -> productMapper.toMotorcycleListResponse(motorcycle, imagesByVariant));
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