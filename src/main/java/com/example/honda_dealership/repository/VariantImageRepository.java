package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.VariantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantImageRepository extends JpaRepository<VariantImage, Long> {

    List<VariantImage> findByVariantId(Long variantId);

    List<VariantImage> findByVariantIdOrderBySortOrderAsc(Long variantId);

    Optional<VariantImage> findByVariantIdAndIsThumbnailTrue(Long variantId);

    Optional<VariantImage> findFirstByVariantIdOrderBySortOrderDesc(Long variantId);

    @Query("SELECT vi FROM VariantImage vi WHERE vi.variant.id IN :variantIds")
    List<VariantImage> findByVariantIdIn(@Param("variantIds") List<Long> variantIds);
}