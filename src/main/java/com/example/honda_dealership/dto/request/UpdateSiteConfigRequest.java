package com.example.honda_dealership.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSiteConfigRequest {

    private String logo;

    private String shopName;

    private String primaryColor;

    private String secondaryColor;

    private String banner;

    private String slogan;

    private String favicon;

    private String heroTitle;

    private String heroSubtitle;

    private String ctaPrimaryText;

    private String ctaPrimaryLink;

    private String ctaSecondaryText;

    private String ctaSecondaryLink;
}