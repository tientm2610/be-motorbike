package com.example.honda_dealership.repository;

import com.example.honda_dealership.entity.Order;
import com.example.honda_dealership.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long userId, OrderStatus status, Pageable pageable);

    List<Order> findByStatus(OrderStatus status);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Optional<Order> findByOrderCode(String orderCode);

    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Order> findByUser_FullNameContainingIgnoreCase(String fullName, Pageable pageable);

    Page<Order> findByUser_EmailContainingIgnoreCase(String email, Pageable pageable);

    Page<Order> findByStatusAndUser_FullNameContainingIgnoreCase(OrderStatus status, String fullName, Pageable pageable);

    long countByStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = :status")
    BigDecimal sumTotalAmountByStatus(@Param("status") OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.createdAt >= :startDate AND o.status = :status")
    BigDecimal sumTotalAmountByStatusAndCreatedAtAfter(@Param("status") OrderStatus status, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT DATE(o.createdAt) as date, SUM(o.totalAmount) as revenue, COUNT(o) as orderCount " +
           "FROM Order o WHERE o.createdAt >= :startDate AND o.status = 'DELIVERED' " +
           "GROUP BY DATE(o.createdAt) ORDER BY date ASC")
    List<Object[]> getDailyRevenue(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT o.motorcycle.id, o.motorcycle.name, " +
           "SUM(o.quantity) as totalSold, COUNT(DISTINCT o.order.id) as totalOrders " +
           "FROM OrderItem o WHERE o.order.status = 'DELIVERED' " +
           "GROUP BY o.motorcycle.id, o.motorcycle.name " +
           "ORDER BY totalSold DESC")
    List<Object[]> findTopSellingProducts(Pageable pageable);
}