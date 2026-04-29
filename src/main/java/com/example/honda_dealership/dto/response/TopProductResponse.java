package com.example.honda_dealership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopProductResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private Long totalSold;
    private Long totalOrders;
}