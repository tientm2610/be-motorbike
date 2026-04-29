package com.example.honda_dealership.dto.response;

import com.example.honda_dealership.entity.enums.VariantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariantResponse {

    private Long id;
    private Long motorcycleId;
    private String sku;
    private String variantName;
    private String colorName;
    private String colorCode;
    private BigDecimal extraPrice;
    private Integer stockQuantity;
    private String imageUrl;
    private VariantStatus status;
    private BigDecimal effectivePrice;
    private LocalDateTime createdAt;
}