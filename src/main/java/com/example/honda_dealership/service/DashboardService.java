package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.response.DashboardSummaryResponse;
import com.example.honda_dealership.dto.response.OrderStatusCountResponse;
import com.example.honda_dealership.dto.response.RevenueResponse;
import com.example.honda_dealership.dto.response.TopProductResponse;
import com.example.honda_dealership.entity.enums.OrderStatus;
import com.example.honda_dealership.repository.OrderRepository;
import com.example.honda_dealership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary() {
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        BigDecimal totalRevenue = orderRepository.sumTotalAmountByStatus(OrderStatus.DELIVERED);
        long pendingOrders = orderRepository.countByStatus(OrderStatus.PENDING);
        long deliveredOrders = orderRepository.countByStatus(OrderStatus.DELIVERED);
        long cancelledOrders = orderRepository.countByStatus(OrderStatus.CANCELLED);

        return DashboardSummaryResponse.builder()
                .totalUsers(totalUsers)
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
                .pendingOrders(pendingOrders)
                .deliveredOrders(deliveredOrders)
                .cancelledOrders(cancelledOrders)
                .build();
    }

    @Transactional(readOnly = true)
    public List<RevenueResponse> getRevenue(String range) {
        LocalDateTime startDate = calculateStartDate(range);

        List<Object[]> results = orderRepository.getDailyRevenue(startDate);

        return results.stream()
                .map(row -> RevenueResponse.builder()
                        .date(((java.sql.Date) row[0]).toLocalDate())
                        .revenue((BigDecimal) row[1])
                        .orderCount(((Long) row[2]))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TopProductResponse> getTopProducts() {
        List<Object[]> results = orderRepository.findTopSellingProducts(PageRequest.of(0, 10));

        return results.stream()
                .map(row -> TopProductResponse.builder()
                        .id((Long) row[0])
                        .name((String) row[1])
                        .imageUrl((String) row[2])
                        .totalSold((Long) row[3])
                        .totalOrders((Long) row[4])
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderStatusCountResponse getOrderStatusCounts() {
        Map<OrderStatus, Long> statusCounts = new HashMap<>();

        for (OrderStatus status : OrderStatus.values()) {
            long count = orderRepository.countByStatus(status);
            statusCounts.put(status, count);
        }

        long total = orderRepository.count();

        return OrderStatusCountResponse.builder()
                .statusCounts(statusCounts)
                .total(total)
                .build();
    }

    private LocalDateTime calculateStartDate(String range) {
        if (range == null) {
            range = "7days";
        }

        LocalDateTime now = LocalDateTime.now();

        return switch (range.toLowerCase()) {
            case "today" -> now.withHour(0).withMinute(0).withSecond(0);
            case "7days" -> now.minusDays(7);
            case "30days" -> now.minusDays(30);
            case "90days" -> now.minusDays(90);
            default -> now.minusDays(7);
        };
    }
}