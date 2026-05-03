package com.example.honda_dealership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponse {

    private HeroResponse hero;
    private List<MotorcycleSummaryResponse> featuredMotorcycles;
    private List<CategorySummaryResponse> categories;
}