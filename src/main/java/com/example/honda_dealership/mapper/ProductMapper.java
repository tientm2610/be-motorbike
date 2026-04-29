package com.example.honda_dealership.mapper;

import com.example.honda_dealership.dto.response.BrandResponse;
import com.example.honda_dealership.dto.response.CategoryResponse;
import com.example.honda_dealership.dto.response.MotorcycleResponse;
import com.example.honda_dealership.dto.response.VariantResponse;
import com.example.honda_dealership.entity.Brand;
import com.example.honda_dealership.entity.Category;
import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.MotorcycleVariant;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
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
                .basePrice(motorcycle.getBasePrice())
                .description(motorcycle.getDescription())
                .thumbnailUrl(motorcycle.getThumbnailUrl())
                .status(motorcycle.getStatus())
                .brand(toBrandResponse(motorcycle.getBrand()))
                .categories(motorcycle.getCategories().stream()
                        .map(this::toCategoryResponse)
                        .collect(Collectors.toList()))
                .variants(motorcycle.getVariants().stream()
                        .map(this::toVariantResponse)
                        .collect(Collectors.toList()))
                .totalStock(totalStock)
                .createdAt(motorcycle.getCreatedAt())
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
                .basePrice(motorcycle.getBasePrice())
                .thumbnailUrl(motorcycle.getThumbnailUrl())
                .status(motorcycle.getStatus())
                .brand(toBrandResponse(motorcycle.getBrand()))
                .totalStock(totalStock)
                .createdAt(motorcycle.getCreatedAt())
                .build();
    }

    public VariantResponse toVariantResponse(MotorcycleVariant variant) {
        if (variant == null) {
            return null;
        }

        BigDecimal effectivePrice = variant.getMotorcycle().getBasePrice()
                .add(variant.getExtraPrice() != null ? variant.getExtraPrice() : BigDecimal.ZERO);

        return VariantResponse.builder()
                .id(variant.getId())
                .motorcycleId(variant.getMotorcycle().getId())
                .sku(variant.getSku())
                .variantName(variant.getVariantName())
                .colorName(variant.getColorName())
                .colorCode(variant.getColorCode())
                .extraPrice(variant.getExtraPrice())
                .stockQuantity(variant.getStockQuantity())
                .imageUrl(variant.getImageUrl())
                .status(variant.getStatus())
                .effectivePrice(effectivePrice)
                .createdAt(variant.getCreatedAt())
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
}