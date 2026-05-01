package com.example.honda_dealership.dto.response;

import com.example.honda_dealership.entity.enums.VariantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private BigDecimal price;
    private Integer stockQuantity;
    private VariantStatus status;
    private List<VariantImageResponse> images;
    private LocalDateTime createdAt;
}