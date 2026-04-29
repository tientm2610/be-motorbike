package com.example.honda_dealership.dto.request;

import com.example.honda_dealership.entity.enums.VariantStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVariantRequest {

    @NotNull(message = "Motorcycle ID is required")
    private Long motorcycleId;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String variantName;
    private String colorName;
    private String colorCode;

    private BigDecimal extraPrice;

    @NotNull(message = "Stock quantity is required")
    @Positive(message = "Stock quantity must be positive")
    private Integer stockQuantity;

    private String imageUrl;

    private VariantStatus status;
}