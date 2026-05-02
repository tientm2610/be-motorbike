package com.example.honda_dealership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotorcycleListResponse {

    private Long id;
    private String name;
    private String slug;
    private Long minPrice;
    private String thumbnailUrl;
    private Integer totalStock;
    private String brandName;
    private String categoryName;
}