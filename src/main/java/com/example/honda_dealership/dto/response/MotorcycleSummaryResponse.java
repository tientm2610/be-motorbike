package com.example.honda_dealership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotorcycleSummaryResponse {

    private Long id;
    private String name;
    private String slug;
    private String thumbnailUrl;
    private Long minPrice;
    private Boolean isAvailable;
    private String categoryName;
}