package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.request.AddCartItemRequest;
import com.example.honda_dealership.dto.request.UpdateCartItemRequest;
import com.example.honda_dealership.dto.response.CartItemResponse;
import com.example.honda_dealership.dto.response.CartResponse;
import com.example.honda_dealership.entity.CartItem;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.User;
import com.example.honda_dealership.entity.enums.VariantStatus;
import com.example.honda_dealership.exception.AppException;
import com.example.honda_dealership.enums.ResponseCode;
import com.example.honda_dealership.repository.CartItemRepository;
import com.example.honda_dealership.repository.MotorcycleVariantRepository;
import com.example.honda_dealership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final MotorcycleVariantRepository variantRepository;
    private final UserRepository userRepository;

    @Transactional
    public CartItemResponse addItem(Long userId, AddCartItemRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ResponseCode.USER_NOT_FOUND));

        MotorcycleVariant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));

        if (variant.getStatus() != VariantStatus.AVAILABLE) {
            throw new AppException(ResponseCode.BAD_REQUEST, "Variant is not available");
        }

        if (request.getQuantity() > variant.getStockQuantity()) {
            throw new AppException(ResponseCode.BAD_REQUEST, "Insufficient stock. Available: " + variant.getStockQuantity());
        }

        BigDecimal unitPrice = calculateEffectivePrice(variant);

        CartItem existingItem = cartItemRepository.findByUserIdAndVariantId(userId, request.getVariantId())
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            if (newQuantity > variant.getStockQuantity()) {
                throw new AppException(ResponseCode.BAD_REQUEST, "Insufficient stock for quantity update");
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
            return mapToResponse(existingItem);
        }

        CartItem cartItem = CartItem.builder()
                .user(user)
                .variant(variant)
                .quantity(request.getQuantity())
                .unitPriceSnapshot(unitPrice)
                .build();

        cartItem = cartItemRepository.save(cartItem);
        return mapToResponse(cartItem);
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        List<CartItemResponse> itemResponses = cartItems.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        int totalQuantity = cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        BigDecimal subtotal = cartItems.stream()
                .map(item -> item.getUnitPriceSnapshot().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .items(itemResponses)
                .totalQuantity(totalQuantity)
                .subtotal(subtotal)
                .estimatedTotal(subtotal)
                .build();
    }

    @Transactional
    public CartItemResponse updateItemQuantity(Long userId, Long itemId, UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));

        if (!cartItem.getUser().getId().equals(userId)) {
            throw new AppException(ResponseCode.ACCESS_DENIED);
        }

        if (request.getQuantity() == 0) {
            cartItemRepository.delete(cartItem);
            return null;
        }

        MotorcycleVariant variant = cartItem.getVariant();
        if (request.getQuantity() > variant.getStockQuantity()) {
            throw new AppException(ResponseCode.BAD_REQUEST, "Insufficient stock. Available: " + variant.getStockQuantity());
        }

        cartItem.setQuantity(request.getQuantity());
        cartItem = cartItemRepository.save(cartItem);
        return mapToResponse(cartItem);
    }

    @Transactional
    public void removeItem(Long userId, Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ResponseCode.RESOURCE_NOT_FOUND));

        if (!cartItem.getUser().getId().equals(userId)) {
            throw new AppException(ResponseCode.ACCESS_DENIED);
        }

        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(cartItems);
    }

    private BigDecimal calculateEffectivePrice(MotorcycleVariant variant) {
        BigDecimal basePrice = variant.getMotorcycle().getBasePrice();
        BigDecimal extraPrice = variant.getExtraPrice() != null ? variant.getExtraPrice() : BigDecimal.ZERO;
        return basePrice.add(extraPrice);
    }

    private CartItemResponse mapToResponse(CartItem cartItem) {
        MotorcycleVariant variant = cartItem.getVariant();
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .variantId(variant.getId())
                .variantName(variant.getVariantName())
                .colorName(variant.getColorName())
                .imageUrl(variant.getImageUrl())
                .motorcycleName(variant.getMotorcycle().getName())
                .motorcycleSlug(variant.getMotorcycle().getSlug())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPriceSnapshot())
                .subtotal(cartItem.getUnitPriceSnapshot().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .build();
    }
}