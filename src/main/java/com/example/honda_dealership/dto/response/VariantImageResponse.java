package com.example.honda_dealership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariantImageResponse {

    private Long id;
    private String imageUrl;
    private String publicId;
    private Integer sortOrder;
    private Boolean isThumbnail;
    private LocalDateTime createdAt;
}