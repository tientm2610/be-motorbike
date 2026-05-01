package com.example.honda_dealership.dto.request;

import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMotorcycleRequest {

    private String name;
    private String code;
    private String slug;
    private String description;
    private String specsJson;
    private Long brandId;
    private Long categoryId;
    private MotorcycleStatus status;
}