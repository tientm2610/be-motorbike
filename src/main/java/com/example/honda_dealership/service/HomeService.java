package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.response.*;
import com.example.honda_dealership.entity.Category;
import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.SiteConfig;
import com.example.honda_dealership.entity.VariantImage;
import com.example.honda_dealership.repository.CategoryRepository;
import com.example.honda_dealership.repository.MotorcycleRepository;
import com.example.honda_dealership.repository.SiteConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final SiteConfigRepository siteConfigRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public HomeResponse getHomeData() {
        HeroResponse hero = getHero();
        List<MotorcycleSummaryResponse> featuredMotorcycles = getFeaturedMotorcycles(4);
        List<CategorySummaryResponse> categories = getCategories(4);

        return HomeResponse.builder()
                .hero(hero)
                .featuredMotorcycles(featuredMotorcycles)
                .categories(categories)
                .build();
    }

    private HeroResponse getHero() {
        Optional<SiteConfig> config = siteConfigRepository.findFirstBy();
        
        if (config.isEmpty()) {
            return HeroResponse.builder()
                    .title("Ride Your Dream Bike")
                    .subtitle("Discover premium Honda motorcycles with expert guidance")
                    .ctaPrimaryText("Khám phá xe máy")
                    .ctaPrimaryLink("/motorcycles")
                    .ctaSecondaryText("Tìm hiểu thêm")
                    .ctaSecondaryLink("/about")
                    .build();
        }

        SiteConfig siteConfig = config.get();
        return HeroResponse.builder()
                .heroImage(siteConfig.getBanner())
                .title(siteConfig.getHeroTitle())
                .subtitle(siteConfig.getHeroSubtitle())
                .ctaPrimaryText(siteConfig.getCtaPrimaryText())
                .ctaPrimaryLink(siteConfig.getCtaPrimaryLink())
                .ctaSecondaryText(siteConfig.getCtaSecondaryText())
                .ctaSecondaryLink(siteConfig.getCtaSecondaryLink())
                .build();
    }

    private List<MotorcycleSummaryResponse> getFeaturedMotorcycles(int limit) {
        List<Motorcycle> motorcycles = motorcycleRepository.findAll();
        
        if (motorcycles.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(motorcycles);
        
        return motorcycles.stream()
                .limit(limit)
                .map(this::mapToMotorcycleSummary)
                .collect(Collectors.toList());
    }

    private MotorcycleSummaryResponse mapToMotorcycleSummary(Motorcycle motorcycle) {
        String thumbnailUrl = getThumbnailUrl(motorcycle);
        Long minPrice = getMinPrice(motorcycle);
        Boolean isAvailable = checkIsAvailable(motorcycle);
        String categoryName = motorcycle.getCategory() != null ? 
                motorcycle.getCategory().getName() : null;

        return MotorcycleSummaryResponse.builder()
                .id(motorcycle.getId())
                .name(motorcycle.getName())
                .slug(motorcycle.getSlug())
                .thumbnailUrl(thumbnailUrl)
                .minPrice(minPrice)
                .isAvailable(isAvailable)
                .categoryName(categoryName)
                .build();
    }

    private String getThumbnailUrl(Motorcycle motorcycle) {
        return motorcycle.getVariants().stream()
                .flatMap(variant -> variant.getImages().stream())
                .filter(image -> Boolean.TRUE.equals(image.getIsThumbnail()))
                .map(VariantImage::getImageUrl)
                .findFirst()
                .orElseGet(() -> motorcycle.getVariants().stream()
                        .flatMap(variant -> variant.getImages().stream())
                        .min(Comparator.comparingInt(VariantImage::getSortOrder))
                        .map(VariantImage::getImageUrl)
                        .orElse(null));
    }

    private Long getMinPrice(Motorcycle motorcycle) {
        return motorcycle.getVariants().stream()
                .map(variant -> variant.getPrice().longValue())
                .min(Comparator.naturalOrder())
                .orElse(0L);
    }

    private Boolean checkIsAvailable(Motorcycle motorcycle) {
        return motorcycle.getVariants().stream()
                .anyMatch(variant -> variant.getStockQuantity() > 0);
    }

    private List<CategorySummaryResponse> getCategories(int limit) {
        List<Category> categories = categoryRepository.findAll();
        
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }

        return categories.stream()
                .limit(limit)
                .map(this::mapToCategorySummary)
                .collect(Collectors.toList());
    }

    private CategorySummaryResponse mapToCategorySummary(Category category) {
        long productCount = category.getMotorcycles() != null ? 
                category.getMotorcycles().size() : 0;

        return CategorySummaryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .productCount(productCount)
                .build();
    }
}