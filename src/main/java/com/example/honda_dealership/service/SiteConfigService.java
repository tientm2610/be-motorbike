package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.request.UpdateSiteConfigRequest;
import com.example.honda_dealership.dto.response.SiteConfigResponse;
import com.example.honda_dealership.entity.SiteConfig;
import com.example.honda_dealership.repository.SiteConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteConfigService {

    private final SiteConfigRepository siteConfigRepository;

    @Transactional(readOnly = true)
    public SiteConfigResponse getConfig() {
        Optional<SiteConfig> config = siteConfigRepository.findFirstBy();
        
        if (config.isEmpty()) {
            SiteConfig newConfig = SiteConfig.builder()
                    .shopName("Honda Dealership")
                    .primaryColor("#e31837")
                    .secondaryColor("#ffffff")
                    .heroTitle("Ride Your Dream Bike")
                    .heroSubtitle("Discover premium Honda motorcycles with expert guidance, competitive pricing, and unparalleled service.")
                    .ctaPrimaryText("Khám phá xe máy")
                    .ctaPrimaryLink("/motorcycles")
                    .ctaSecondaryText("Tìm hiểu thêm")
                    .ctaSecondaryLink("/about")
                    .build();
            newConfig = siteConfigRepository.save(newConfig);
            return mapToResponse(newConfig);
        }
        
        return mapToResponse(config.get());
    }

    @Transactional
    public SiteConfigResponse updateConfig(UpdateSiteConfigRequest request) {
        SiteConfig config = siteConfigRepository.findFirstBy()
                .orElseGet(() -> SiteConfig.builder().build());

        if (request.getLogo() != null) {
            config.setLogo(request.getLogo());
        }
        if (request.getShopName() != null) {
            config.setShopName(request.getShopName());
        }
        if (request.getPrimaryColor() != null) {
            config.setPrimaryColor(request.getPrimaryColor());
        }
        if (request.getSecondaryColor() != null) {
            config.setSecondaryColor(request.getSecondaryColor());
        }
        if (request.getBanner() != null) {
            config.setBanner(request.getBanner());
        }
        if (request.getSlogan() != null) {
            config.setSlogan(request.getSlogan());
        }
        if (request.getFavicon() != null) {
            config.setFavicon(request.getFavicon());
        }
        if (request.getHeroTitle() != null) {
            config.setHeroTitle(request.getHeroTitle());
        }
        if (request.getHeroSubtitle() != null) {
            config.setHeroSubtitle(request.getHeroSubtitle());
        }
        if (request.getCtaPrimaryText() != null) {
            config.setCtaPrimaryText(request.getCtaPrimaryText());
        }
        if (request.getCtaPrimaryLink() != null) {
            config.setCtaPrimaryLink(request.getCtaPrimaryLink());
        }
        if (request.getCtaSecondaryText() != null) {
            config.setCtaSecondaryText(request.getCtaSecondaryText());
        }
        if (request.getCtaSecondaryLink() != null) {
            config.setCtaSecondaryLink(request.getCtaSecondaryLink());
        }

        config = siteConfigRepository.save(config);
        return mapToResponse(config);
    }

    private SiteConfigResponse mapToResponse(SiteConfig config) {
        return SiteConfigResponse.builder()
                .id(config.getId())
                .logo(config.getLogo())
                .shopName(config.getShopName())
                .primaryColor(config.getPrimaryColor())
                .secondaryColor(config.getSecondaryColor())
                .banner(config.getBanner())
                .slogan(config.getSlogan())
                .favicon(config.getFavicon())
                .heroTitle(config.getHeroTitle())
                .heroSubtitle(config.getHeroSubtitle())
                .ctaPrimaryText(config.getCtaPrimaryText())
                .ctaPrimaryLink(config.getCtaPrimaryLink())
                .ctaSecondaryText(config.getCtaSecondaryText())
                .ctaSecondaryLink(config.getCtaSecondaryLink())
                .build();
    }
}