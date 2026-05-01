package com.example.honda_dealership.dto.request;

import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String description;
    private String specsJson;

    @NotNull(message = "Brand ID is required")
    private Long brandId;

    private Long categoryId;

    private MotorcycleStatus status;
}