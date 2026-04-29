package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.Order;
import com.example.honda_dealership.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    List<Order> findByStatus(OrderStatus status);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Optional<Order> findByOrderCode(String orderCode);

    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Order> findByUser_FullNameContainingIgnoreCase(String fullName, Pageable pageable);

    Page<Order> findByUser_EmailContainingIgnoreCase(String email, Pageable pageable);

    Page<Order> findByStatusAndUser_FullNameContainingIgnoreCase(OrderStatus status, String fullName, Pageable pageable);
}