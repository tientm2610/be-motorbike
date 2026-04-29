package com.example.honda_dealership.dto.response;

import com.example.honda_dealership.entity.enums.MotorcycleStatus;
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
public class MotorcycleResponse {

    private Long id;
    private String name;
    private String code;
    private String slug;
    private BigDecimal basePrice;
    private String description;
    private String thumbnailUrl;
    private MotorcycleStatus status;
    private BrandResponse brand;
    private List<CategoryResponse> categories;
    private List<VariantResponse> variants;
    private Integer totalStock;
    private LocalDateTime createdAt;
}