package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.request.CreateMotorcycleRequest;
import com.example.honda_dealership.dto.request.CreateVariantImageRequest;
import com.example.honda_dealership.dto.request.CreateVariantRequest;
import com.example.honda_dealership.dto.request.UpdateMotorcycleRequest;
import com.example.honda_dealership.dto.response.BrandResponse;
import com.example.honda_dealership.dto.response.CategoryResponse;
import com.example.honda_dealership.dto.response.MotorcycleResponse;
import com.example.honda_dealership.dto.response.VariantImageResponse;
import com.example.honda_dealership.dto.response.VariantResponse;
import com.example.honda_dealership.entity.Brand;
import com.example.honda_dealership.entity.Category;
import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.VariantImage;
import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import com.example.honda_dealership.entity.enums.VariantStatus;
import com.example.honda_dealership.exception.BadRequestException;
import com.example.honda_dealership.exception.ResourceNotFoundException;
import com.example.honda_dealership.mapper.ProductMapper;
import com.example.honda_dealership.repository.BrandRepository;
import com.example.honda_dealership.repository.CategoryRepository;
import com.example.honda_dealership.repository.MotorcycleRepository;
import com.example.honda_dealership.repository.MotorcycleVariantRepository;
import com.example.honda_dealership.repository.VariantImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleVariantRepository variantRepository;
    private final VariantImageRepository variantImageRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public MotorcycleResponse createMotorcycle(CreateMotorcycleRequest request) {
        if (motorcycleRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Motorcycle code already exists");
        }

        if (motorcycleRepository.existsBySlug(request.getSlug())) {
            throw new BadRequestException("Motorcycle slug already exists");
        }

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        Motorcycle motorcycle = Motorcycle.builder()
                .name(request.getName())
                .code(request.getCode())
                .slug(request.getSlug())
                .description(request.getDescription())
                .specsJson(request.getSpecsJson())
                .brand(brand)
                .status(request.getStatus() != null ? request.getStatus() : MotorcycleStatus.ACTIVE)
                .build();

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            motorcycle.setCategory(category);
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
        if (request.getSlug() != null && !request.getSlug().equals(motorcycle.getSlug())) {
            if (motorcycleRepository.existsBySlug(request.getSlug())) {
                throw new BadRequestException("Motorcycle slug already exists");
            }
            motorcycle.setSlug(request.getSlug());
        }
        if (request.getDescription() != null) {
            motorcycle.setDescription(request.getDescription());
        }
        if (request.getSpecsJson() != null) {
            motorcycle.setSpecsJson(request.getSpecsJson());
        }
        if (request.getStatus() != null) {
            motorcycle.setStatus(request.getStatus());
        }

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
            motorcycle.setBrand(brand);
        }

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            motorcycle.setCategory(category);
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
        if (variantRepository.existsBySku(request.getSku())) {
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
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .status(request.getStatus() != null ? request.getStatus() : VariantStatus.AVAILABLE)
                .build();

        variant = variantRepository.save(variant);
        return productMapper.toVariantResponse(variant);
    }

    @Transactional
    public VariantResponse updateVariant(Long id, BigDecimal price, Integer stockQuantity, VariantStatus status) {
        MotorcycleVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));

        if (price != null) {
            variant.setPrice(price);
        }
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

    @Transactional
    public VariantImageResponse addVariantImage(CreateVariantImageRequest request) {
        MotorcycleVariant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));

        if (Boolean.TRUE.equals(request.getIsThumbnail())) {
            variantImageRepository.findByVariantIdAndIsThumbnailTrue(request.getVariantId())
                    .ifPresent(existingThumbnail -> {
                        existingThumbnail.setIsThumbnail(false);
                        variantImageRepository.save(existingThumbnail);
                    });
        }

        VariantImage image = VariantImage.builder()
                .variant(variant)
                .imageUrl(request.getImageUrl())
                .publicId(request.getPublicId())
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .isThumbnail(request.getIsThumbnail() != null ? request.getIsThumbnail() : false)
                .build();

        image = variantImageRepository.save(image);
        return productMapper.toVariantImageResponse(image);
    }

    @Transactional
    public void deleteVariantImage(Long id) {
        if (!variantImageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Image not found");
        }
        variantImageRepository.deleteById(id);
    }
}