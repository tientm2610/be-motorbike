package com.example.honda_dealership.dto.request;

import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateMotorcycleRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Slug is required")
    private String slug;

    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;

    private String description;
    private String specsJson;
    private String thumbnailUrl;

    @NotNull(message = "Brand ID is required")
    private Long brandId;

    private List<Long> categoryIds;

    private MotorcycleStatus status;
}