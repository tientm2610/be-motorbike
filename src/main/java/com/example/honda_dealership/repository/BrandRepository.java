package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByName(String name);

    List<Brand> findByNameContainingIgnoreCase(String keyword);
}