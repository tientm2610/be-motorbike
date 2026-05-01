package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.enums.VariantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotorcycleVariantRepository extends JpaRepository<MotorcycleVariant, Long> {

    List<MotorcycleVariant> findByMotorcycleId(Long motorcycleId);

    Optional<MotorcycleVariant> findBySku(String sku);

    boolean existsBySku(String sku);

    List<MotorcycleVariant> findByStatus(VariantStatus status);

    @Query("SELECT v FROM MotorcycleVariant v WHERE v.stockQuantity <= :quantity AND v.status = :status")
    List<MotorcycleVariant> findLowStockVariants(@Param("quantity") Integer quantity, @Param("status") VariantStatus status);
}