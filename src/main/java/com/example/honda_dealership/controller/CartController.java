package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.request.AddCartItemRequest;
import com.example.honda_dealership.dto.request.UpdateCartItemRequest;
import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.CartItemResponse;
import com.example.honda_dealership.dto.response.CartResponse;
import com.example.honda_dealership.security.CustomUserDetails;
import com.example.honda_dealership.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
// @PreAuthorize("hasRole('CUSTOMER')")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartItemResponse>> addItem(
            @Valid @RequestBody AddCartItemRequest request,
            Authentication authentication
    ) {
        Long userId = getUserId(authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(cartService.addItem(userId, request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart(Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(ApiResponse.success(cartService.getCart(userId)));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartItemResponse>> updateItemQuantity(
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request,
            Authentication authentication
    ) {
        Long userId = getUserId(authentication);
        CartItemResponse response = cartService.updateItemQuantity(userId, itemId, request);
        if (response == null) {
            return ResponseEntity.ok(ApiResponse.<CartItemResponse>builder()
                    .code(1000)
                    .message("Item removed")
                    .result(null)
                    .build());
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(
            @PathVariable Long itemId,
            Authentication authentication
    ) {
        Long userId = getUserId(authentication);
        cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(Authentication authentication) {
        Long userId = getUserId(authentication);
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    private Long getUserId(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser().getId();
    }
}