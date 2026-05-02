package com.example.honda_dealership.mapper;

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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public MotorcycleResponse toMotorcycleResponse(Motorcycle motorcycle) {
        if (motorcycle == null) {
            return null;
        }

        Integer totalStock = motorcycle.getVariants().stream()
                .mapToInt(MotorcycleVariant::getStockQuantity)
                .sum();

        return MotorcycleResponse.builder()
                .id(motorcycle.getId())
                .name(motorcycle.getName())
                .code(motorcycle.getCode())
                .slug(motorcycle.getSlug())
                .description(motorcycle.getDescription())
                .specsJson(motorcycle.getSpecsJson())
                .status(motorcycle.getStatus())
                .brand(toBrandResponse(motorcycle.getBrand()))
                .category(toCategoryResponse(motorcycle.getCategory()))
                .variants(motorcycle.getVariants().stream()
                        .map(this::toVariantResponse)
                        .collect(Collectors.toList()))
                .totalStock(totalStock)
                .createdAt(motorcycle.getCreatedAt())
                .updatedAt(motorcycle.getUpdatedAt())
                .build();
    }

    public MotorcycleResponse toMotorcycleSummaryResponse(Motorcycle motorcycle) {
        if (motorcycle == null) {
            return null;
        }

        Integer totalStock = motorcycle.getVariants().stream()
                .mapToInt(MotorcycleVariant::getStockQuantity)
                .sum();

        return MotorcycleResponse.builder()
                .id(motorcycle.getId())
                .name(motorcycle.getName())
                .code(motorcycle.getCode())
                .slug(motorcycle.getSlug())
                .status(motorcycle.getStatus())
                .brand(toBrandResponse(motorcycle.getBrand()))
                .category(toCategoryResponse(motorcycle.getCategory()))
                .totalStock(totalStock)
                .createdAt(motorcycle.getCreatedAt())
                .build();
    }

    public VariantResponse toVariantResponse(MotorcycleVariant variant) {
        if (variant == null) {
            return null;
        }

        return VariantResponse.builder()
                .id(variant.getId())
                .motorcycleId(variant.getMotorcycle().getId())
                .sku(variant.getSku())
                .variantName(variant.getVariantName())
                .colorName(variant.getColorName())
                .colorCode(variant.getColorCode())
                .price(variant.getPrice())
                .stockQuantity(variant.getStockQuantity())
                .status(variant.getStatus())
                .images(variant.getImages().stream()
                        .map(this::toVariantImageResponse)
                        .collect(Collectors.toList()))
                .createdAt(variant.getCreatedAt())
                .build();
    }

    public VariantImageResponse toVariantImageResponse(VariantImage image) {
        if (image == null) {
            return null;
        }

        return VariantImageResponse.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .publicId(image.getPublicId())
                .sortOrder(image.getSortOrder())
                .isThumbnail(image.getIsThumbnail())
                .createdAt(image.getCreatedAt())
                .build();
    }

    public BrandResponse toBrandResponse(Brand brand) {
        if (brand == null) {
            return null;
        }

        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .logoUrl(brand.getLogoUrl())
                .description(brand.getDescription())
                .createdAt(brand.getCreatedAt())
                .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .build();
    }

    public MotorcycleListResponse toMotorcycleListResponse(Motorcycle motorcycle) {
        if (motorcycle == null) {
            return null;
        }

        Long minPrice = motorcycle.getVariants().stream()
                .map(MotorcycleVariant::getPrice)
                .filter(price -> price != null)
                .map(BigDecimal::longValue)
                .min(Long::compare)
                .orElse(null);

        Integer totalStock = motorcycle.getVariants().stream()
                .mapToInt(MotorcycleVariant::getStockQuantity)
                .sum();

        String thumbnailUrl = motorcycle.getVariants().stream()
                .flatMap(variant -> variant.getImages().stream())
                .filter(image -> Boolean.TRUE.equals(image.getIsThumbnail()))
                .map(VariantImage::getImageUrl)
                .findFirst()
                .orElseGet(() -> motorcycle.getVariants().stream()
                        .flatMap(variant -> variant.getImages().stream())
                        .min(Comparator.comparingInt(VariantImage::getSortOrder))
                        .map(VariantImage::getImageUrl)
                        .orElse(null));

        return MotorcycleListResponse.builder()
                .id(motorcycle.getId())
                .name(motorcycle.getName())
                .slug(motorcycle.getSlug())
                .minPrice(minPrice)
                .thumbnailUrl(thumbnailUrl)
                .totalStock(totalStock)
                .brandName(motorcycle.getBrand() != null ? motorcycle.getBrand().getName() : null)
                .categoryName(motorcycle.getCategory() != null ? motorcycle.getCategory().getName() : null)
                .build();
    }

    public MotorcycleListResponse toMotorcycleListResponse(Motorcycle motorcycle, Map<Long, List<VariantImage>> imagesByVariant) {
        if (motorcycle == null) {
            return null;
        }

        Long minPrice = motorcycle.getVariants().stream()
                .map(MotorcycleVariant::getPrice)
                .filter(price -> price != null)
                .map(BigDecimal::longValue)
                .min(Long::compare)
                .orElse(null);

        Integer totalStock = motorcycle.getVariants().stream()
                .mapToInt(MotorcycleVariant::getStockQuantity)
                .sum();

        String thumbnailUrl = motorcycle.getVariants().stream()
                .flatMap(variant -> {
                    List<VariantImage> images = imagesByVariant.getOrDefault(variant.getId(), List.of());
                    return images.stream();
                })
                .filter(image -> Boolean.TRUE.equals(image.getIsThumbnail()))
                .map(VariantImage::getImageUrl)
                .findFirst()
                .orElseGet(() -> motorcycle.getVariants().stream()
                        .flatMap(variant -> imagesByVariant.getOrDefault(variant.getId(), List.of()).stream())
                        .min(Comparator.comparingInt(VariantImage::getSortOrder))
                        .map(VariantImage::getImageUrl)
                        .orElse(null));

        return MotorcycleListResponse.builder()
                .id(motorcycle.getId())
                .name(motorcycle.getName())
                .slug(motorcycle.getSlug())
                .minPrice(minPrice)
                .thumbnailUrl(thumbnailUrl)
                .totalStock(totalStock)
                .brandName(motorcycle.getBrand() != null ? motorcycle.getBrand().getName() : null)
                .categoryName(motorcycle.getCategory() != null ? motorcycle.getCategory().getName() : null)
                .build();
    }
}