package com.example.honda_dealership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long id;
    private Long variantId;
    private String variantName;
    private String colorName;
    private String imageUrl;
    private String motorcycleName;
    private String motorcycleSlug;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}