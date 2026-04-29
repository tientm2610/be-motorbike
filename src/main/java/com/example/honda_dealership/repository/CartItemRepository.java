package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserId(Long userId);

    List<CartItem> findBySessionId(String sessionId);

    Optional<CartItem> findByUserIdAndVariantId(Long userId, Long variantId);

    void deleteByUserId(Long userId);
}