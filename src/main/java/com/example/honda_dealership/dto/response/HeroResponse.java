package com.example.honda_dealership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeroResponse {

    private String heroImage;
    private String title;
    private String subtitle;
    private String ctaPrimaryText;
    private String ctaPrimaryLink;
    private String ctaSecondaryText;
    private String ctaSecondaryLink;
}