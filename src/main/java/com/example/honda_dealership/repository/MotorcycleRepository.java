package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long>, JpaSpecificationExecutor<Motorcycle> {

    List<Motorcycle> findByStatus(MotorcycleStatus status);

    Page<Motorcycle> findByStatus(MotorcycleStatus status, Pageable pageable);

    List<Motorcycle> findByBrandId(Long brandId);

    List<Motorcycle> findByCategoryId(Long categoryId);

    List<Motorcycle> findByNameContainingIgnoreCase(String keyword);

    boolean existsByCode(String code);

    boolean existsBySlug(String slug);

    Optional<Motorcycle> findBySlug(String slug);

    @Query("SELECT m FROM Motorcycle m WHERE m.status = :status AND " +
           "(LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.code) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Motorcycle> searchByKeyword(@Param("keyword") String keyword, @Param("status") MotorcycleStatus status);

    @Query("SELECT m FROM Motorcycle m JOIN m.variants v WHERE v.stockQuantity <= :threshold AND m.status = :status")
    List<Motorcycle> findLowStockProducts(@Param("threshold") Integer threshold, @Param("status") MotorcycleStatus status);
}