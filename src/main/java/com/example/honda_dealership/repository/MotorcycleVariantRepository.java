package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.enums.VariantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotorcycleVariantRepository extends JpaRepository<MotorcycleVariant, Long> {

    List<MotorcycleVariant> findByMotorcycleId(Long motorcycleId);

    Optional<MotorcycleVariant> findBySku(String sku);

    List<MotorcycleVariant> findByStatus(VariantStatus status);

    List<MotorcycleVariant> findByStockQuantityGreaterThan(Integer quantity);
}