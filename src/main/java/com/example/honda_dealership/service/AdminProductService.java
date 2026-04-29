package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.request.CreateMotorcycleRequest;
import com.example.honda_dealership.dto.request.CreateVariantRequest;
import com.example.honda_dealership.dto.request.UpdateMotorcycleRequest;
import com.example.honda_dealership.dto.response.BrandResponse;
import com.example.honda_dealership.dto.response.CategoryResponse;
import com.example.honda_dealership.dto.response.MotorcycleResponse;
import com.example.honda_dealership.dto.response.VariantResponse;
import com.example.honda_dealership.entity.Brand;
import com.example.honda_dealership.entity.Category;
import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import com.example.honda_dealership.entity.enums.VariantStatus;
import com.example.honda_dealership.exception.BadRequestException;
import com.example.honda_dealership.exception.ResourceNotFoundException;
import com.example.honda_dealership.mapper.ProductMapper;
import com.example.honda_dealership.repository.BrandRepository;
import com.example.honda_dealership.repository.CategoryRepository;
import com.example.honda_dealership.repository.MotorcycleRepository;
import com.example.honda_dealership.repository.MotorcycleVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleVariantRepository variantRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public MotorcycleResponse createMotorcycle(CreateMotorcycleRequest request) {
        if (motorcycleRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Motorcycle code already exists");
        }

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        Motorcycle motorcycle = Motorcycle.builder()
                .name(request.getName())
                .code(request.getCode())
                .slug(request.getSlug())
                .basePrice(request.getBasePrice())
                .description(request.getDescription())
                .specsJson(request.getSpecsJson())
                .thumbnailUrl(request.getThumbnailUrl())
                .brand(brand)
                .status(request.getStatus() != null ? request.getStatus() : MotorcycleStatus.ACTIVE)
                .build();

        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
            motorcycle.setCategories(new HashSet<>(categories));
        }

        motorcycle = motorcycleRepository.save(motorcycle);
        return productMapper.toMotorcycleResponse(motorcycle);
    }

    @Transactional
    public MotorcycleResponse updateMotorcycle(Long id, UpdateMotorcycleRequest request) {
        Motorcycle motorcycle = motorcycleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorcycle not found"));

        if (request.getName() != null) {
            motorcycle.setName(request.getName());
        }
        if (request.getCode() != null && !request.getCode().equals(motorcycle.getCode())) {
            if (motorcycleRepository.existsByCode(request.getCode())) {
                throw new BadRequestException("Motorcycle code already exists");
            }
            motorcycle.setCode(request.getCode());
        }
        if (request.getSlug() != null) {
            motorcycle.setSlug(request.getSlug());
        }
        if (request.getBasePrice() != null) {
            motorcycle.setBasePrice(request.getBasePrice());
        }
        if (request.getDescription() != null) {
            motorcycle.setDescription(request.getDescription());
        }
        if (request.getSpecsJson() != null) {
            motorcycle.setSpecsJson(request.getSpecsJson());
        }
        if (request.getThumbnailUrl() != null) {
            motorcycle.setThumbnailUrl(request.getThumbnailUrl());
        }
        if (request.getStatus() != null) {
            motorcycle.setStatus(request.getStatus());
        }

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
            motorcycle.setBrand(brand);
        }

        if (request.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
            motorcycle.setCategories(new HashSet<>(categories));
        }

        motorcycle = motorcycleRepository.save(motorcycle);
        return productMapper.toMotorcycleResponse(motorcycle);
    }

    @Transactional
    public void deleteMotorcycle(Long id) {
        if (!motorcycleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Motorcycle not found");
        }
        motorcycleRepository.deleteById(id);
    }

    @Transactional
    public BrandResponse createBrand(String name, String logoUrl, String description) {
        if (brandRepository.existsByName(name)) {
            throw new BadRequestException("Brand name already exists");
        }

        Brand brand = Brand.builder()
                .name(name)
                .logoUrl(logoUrl)
                .description(description)
                .build();

        brand = brandRepository.save(brand);
        return productMapper.toBrandResponse(brand);
    }

    @Transactional
    public CategoryResponse createCategory(String name, String slug, String description) {
        if (categoryRepository.existsByName(name)) {
            throw new BadRequestException("Category name already exists");
        }

        Category category = Category.builder()
                .name(name)
                .slug(slug)
                .description(description)
                .build();

        category = categoryRepository.save(category);
        return productMapper.toCategoryResponse(category);
    }

    @Transactional
    public VariantResponse createVariant(CreateVariantRequest request) {
        if (variantRepository.findBySku(request.getSku()).isPresent()) {
            throw new BadRequestException("SKU already exists");
        }

        Motorcycle motorcycle = motorcycleRepository.findById(request.getMotorcycleId())
                .orElseThrow(() -> new ResourceNotFoundException("Motorcycle not found"));

        MotorcycleVariant variant = MotorcycleVariant.builder()
                .motorcycle(motorcycle)
                .sku(request.getSku())
                .variantName(request.getVariantName())
                .colorName(request.getColorName())
                .colorCode(request.getColorCode())
                .extraPrice(request.getExtraPrice())
                .stockQuantity(request.getStockQuantity())
                .imageUrl(request.getImageUrl())
                .status(request.getStatus() != null ? request.getStatus() : VariantStatus.AVAILABLE)
                .build();

        variant = variantRepository.save(variant);
        return productMapper.toVariantResponse(variant);
    }

    @Transactional
    public VariantResponse updateVariant(Long id, Integer stockQuantity, VariantStatus status) {
        MotorcycleVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));

        if (stockQuantity != null) {
            variant.setStockQuantity(stockQuantity);
        }
        if (status != null) {
            variant.setStatus(status);
        }

        variant = variantRepository.save(variant);
        return productMapper.toVariantResponse(variant);
    }

    @Transactional
    public void deleteVariant(Long id) {
        if (!variantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Variant not found");
        }
        variantRepository.deleteById(id);
    }
}