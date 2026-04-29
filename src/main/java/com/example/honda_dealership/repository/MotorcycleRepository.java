package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long>, JpaSpecificationExecutor<Motorcycle> {

    List<Motorcycle> findByStatus(MotorcycleStatus status);

    Page<Motorcycle> findByStatus(MotorcycleStatus status, Pageable pageable);

    List<Motorcycle> findByBrandId(Long brandId);

    List<Motorcycle> findByNameContainingIgnoreCase(String keyword);

    List<Motorcycle> findByPriceBetween(BigDecimal min, BigDecimal max);

    boolean existsByCode(String code);

    Optional<Motorcycle> findBySlug(String slug);
}