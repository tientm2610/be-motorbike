package com.example.honda_dealership.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "site_config")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logo")
    private String logo;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "primary_color")
    private String primaryColor;

    @Column(name = "secondary_color")
    private String secondaryColor;

    @Column(name = "banner")
    private String banner;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "favicon")
    private String favicon;

    @Column(name = "hero_title")
    private String heroTitle;

    @Column(name = "hero_subtitle")
    private String heroSubtitle;

    @Column(name = "cta_primary_text")
    private String ctaPrimaryText;

    @Column(name = "cta_primary_link")
    private String ctaPrimaryLink;

    @Column(name = "cta_secondary_text")
    private String ctaSecondaryText;

    @Column(name = "cta_secondary_link")
    private String ctaSecondaryLink;
}