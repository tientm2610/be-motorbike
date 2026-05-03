package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.request.CheckoutRequest;
import com.example.honda_dealership.dto.request.UpdateOrderStatusRequest;
import com.example.honda_dealership.dto.response.OrderDetailResponse;
import com.example.honda_dealership.dto.response.OrderItemResponse;
import com.example.honda_dealership.dto.response.OrderResponse;
import com.example.honda_dealership.entity.*;
import com.example.honda_dealership.entity.enums.OrderStatus;
import com.example.honda_dealership.entity.enums.PaymentStatus;
import com.example.honda_dealership.exception.AppException;
import com.example.honda_dealership.enums.ResponseCode;
import com.example.honda_dealership.repository.CartItemRepository;
import com.example.honda_dealership.repository.MotorcycleVariantRepository;
import com.example.honda_dealership.repository.OrderRepository;
import com.example.honda_dealership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final MotorcycleVariantRepository variantRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse checkout(Long userId, CheckoutRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ResponseCode.USER_NOT_FOUND));

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new AppException(ResponseCode.BAD_REQUEST, "Cart is empty");
        }

        for (CartItem item : cartItems) {
            MotorcycleVariant variant = item.getVariant();
            if (variant.getStockQuantity() < item.getQuantity()) {
                throw new AppException(ResponseCode.BAD_REQUEST,
                        "Insufficient stock for " + variant.getVariantName() + ". Available: " + variant.getStockQuantity());
            }
        }

        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getUnitPriceSnapshot().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String orderCode = generateOrderCode();

        Order order = Order.builder()
                .orderCode(orderCode)
                .user(user)
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .shippingName(request.getShippingName())
                .shippingPhone(request.getShippingPhone())
                .shippingAddress(request.getShippingAddress())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .notes(request.getNote())
                .build();

        for (CartItem cartItem : cartItems) {
            MotorcycleVariant variant = cartItem.getVariant();
            BigDecimal unitPrice = cartItem.getUnitPriceSnapshot();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .variant(variant)
                    .motorcycle(variant.getMotorcycle())
                    .productNameSnapshot(variant.getMotorcycle().getName())
                    .variantNameSnapshot(variant.getVariantName())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();

            order.getItems().add(orderItem);

            variant.setStockQuantity(variant.getStockQuantity() - cartItem.getQuantity());
            variantRepository.save(variant);
        }

        cartItemRepository.deleteAll(cartItems);

        order = orderRepository.save(order);
        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> getMyOrders(Long userId, OrderStatus status, Pageable pageable) {
        Page<Order> orders;
        if (status != null) {
            orders = orderRepository.findByUserIdAndStatus(userId, status, pageable);
        } else {
            orders = orderRepository.findByUserId(userId, pageable);
        }
        return orders.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getMyOrderDetail(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));

        if (!order.getUser().getId().equals(userId)) {
            throw new AppException(ResponseCode.ACCESS_DENIED);
        }

        return mapToDetailResponse(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));

        if (!order.getUser().getId().equals(userId)) {
            throw new AppException(ResponseCode.ACCESS_DENIED);
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new AppException(ResponseCode.BAD_REQUEST, "Only pending orders can be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);

        for (OrderItem item : order.getItems()) {
            MotorcycleVariant variant = item.getVariant();
            variant.setStockQuantity(variant.getStockQuantity() + item.getQuantity());
            variantRepository.save(variant);
        }

        order = orderRepository.save(order);
        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(OrderStatus status, Pageable pageable) {
        Page<Order> orders;
        if (status != null) {
            orders = orderRepository.findByStatus(status, pageable);
        } else {
            orders = orderRepository.findAll(pageable);
        }
        return orders.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));
        return mapToDetailResponse(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));

        OrderStatus oldStatus = order.getStatus();
        order.setStatus(request.getStatus());

        if (request.getStatus() == OrderStatus.CANCELLED && oldStatus != OrderStatus.CANCELLED) {
            for (OrderItem item : order.getItems()) {
                MotorcycleVariant variant = item.getVariant();
                variant.setStockQuantity(variant.getStockQuantity() + item.getQuantity());
                variantRepository.save(variant);
            }
        }

        order = orderRepository.save(order);
        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> getStaffOrders(String search, OrderStatus status, Pageable pageable) {
        Page<Order> orders;

        if (search != null && !search.isBlank()) {
            if (status != null) {
                orders = orderRepository.findByStatusAndUser_FullNameContainingIgnoreCase(status, search, pageable);
            } else {
                orders = orderRepository.findByUser_FullNameContainingIgnoreCase(search, pageable);
            }
        } else if (status != null) {
            orders = orderRepository.findByStatus(status, pageable);
        } else {
            orders = orderRepository.findAll(pageable);
        }

        return orders.map(this::mapToResponse);
    }

    @Transactional
    public OrderResponse confirmOrder(Long staffId, Long orderId) {
        return updateStaffOrderStatus(staffId, orderId, OrderStatus.CONFIRMED, OrderStatus.PENDING);
    }

    @Transactional
    public OrderResponse preparingOrder(Long staffId, Long orderId) {
        return updateStaffOrderStatus(staffId, orderId, OrderStatus.PROCESSING, OrderStatus.CONFIRMED);
    }

    @Transactional
    public OrderResponse shippingOrder(Long staffId, Long orderId) {
        return updateStaffOrderStatus(staffId, orderId, OrderStatus.SHIPPED, OrderStatus.PROCESSING);
    }

    @Transactional
    public OrderResponse deliveredOrder(Long staffId, Long orderId) {
        return updateStaffOrderStatus(staffId, orderId, OrderStatus.DELIVERED, OrderStatus.SHIPPED);
    }

    @Transactional
    public OrderResponse staffCancelOrder(Long staffId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new AppException(ResponseCode.BAD_REQUEST, "Cannot cancel delivered orders");
        }

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new AppException(ResponseCode.USER_NOT_FOUND));

        order.setStaff(staff);
        order.setStatus(OrderStatus.CANCELLED);

        for (OrderItem item : order.getItems()) {
            MotorcycleVariant variant = item.getVariant();
            variant.setStockQuantity(variant.getStockQuantity() + item.getQuantity());
            variantRepository.save(variant);
        }

        order = orderRepository.save(order);
        return mapToResponse(order);
    }

    private OrderResponse updateStaffOrderStatus(Long staffId, Long orderId, OrderStatus newStatus, OrderStatus expectedStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));

        if (order.getStatus() != expectedStatus) {
            throw new AppException(ResponseCode.BAD_REQUEST,
                    "Invalid order status. Expected: " + expectedStatus + ", Current: " + order.getStatus());
        }

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new AppException(ResponseCode.USER_NOT_FOUND));

        order.setStaff(staff);
        order.setStatus(newStatus);

        order = orderRepository.save(order);
        return mapToResponse(order);
    }

    private String generateOrderCode() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int randomNum = (int) (Math.random() * 1000);
        String random = String.format("%03d", randomNum);

        return "ORD-" + timestamp + "-" + random;
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .shippingName(order.getShippingName())
                .shippingPhone(order.getShippingPhone())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .notes(order.getNotes())
                .totalItems(order.getItems().stream().mapToInt(OrderItem::getQuantity).sum())
                .createdAt(order.getCreatedAt())
                .build();
    }

    private OrderDetailResponse mapToDetailResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> {
                    String imageUrl = item.getVariant().getImages().stream()
                            .filter(img -> Boolean.TRUE.equals(img.getIsThumbnail()))
                            .findFirst()
                            .map(img -> img.getImageUrl())
                            .orElse(null);
                    return OrderItemResponse.builder()
                            .id(item.getId())
                            .variantId(item.getVariant().getId())
                            .productName(item.getProductNameSnapshot())
                            .variantName(item.getVariantNameSnapshot())
                            .imageUrl(imageUrl)
                            .quantity(item.getQuantity())
                            .unitPrice(item.getUnitPrice())
                            .subtotal(item.getSubtotal())
                            .build();
                })
                .collect(Collectors.toList());

        return OrderDetailResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .shippingName(order.getShippingName())
                .shippingPhone(order.getShippingPhone())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .notes(order.getNotes())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .items(itemResponses)
                .build();
    }
}