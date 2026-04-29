package com.example.honda_dealership.dto.request;

import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMotorcycleRequest {

    private String name;
    private String code;
    private String slug;

    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;

    private String description;
    private String specsJson;
    private String thumbnailUrl;
    private Long brandId;
    private List<Long> categoryIds;
    private MotorcycleStatus status;
}