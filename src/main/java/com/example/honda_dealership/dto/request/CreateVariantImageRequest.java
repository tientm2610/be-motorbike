package com.example.honda_dealership.dto.request;

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
public class CreateVariantImageRequest {

    @NotNull(message = "Variant ID is required")
    private Long variantId;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String publicId;

    @Builder.Default
    private Integer sortOrder = 0;

    @Builder.Default
    private Boolean isThumbnail = false;
}