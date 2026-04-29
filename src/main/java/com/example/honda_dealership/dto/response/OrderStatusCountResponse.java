package com.example.honda_dealership.dto.response;

import com.example.honda_dealership.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusCountResponse {

    private Map<OrderStatus, Long> statusCounts;
    private Long total;
}