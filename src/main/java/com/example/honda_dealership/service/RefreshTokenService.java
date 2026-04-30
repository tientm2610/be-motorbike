package com.example.honda_dealership.service;

import com.example.honda_dealership.dto.response.LoginResponse;
import com.example.honda_dealership.dto.response.RefreshTokenResponse;
import com.example.honda_dealership.entity.RefreshToken;
import com.example.honda_dealership.entity.User;
import com.example.honda_dealership.entity.enums.UserStatus;
import com.example.honda_dealership.exception.UnauthorizedException;
import com.example.honda_dealership.repository.RefreshTokenRepository;
import com.example.honda_dealership.repository.UserRepository;
import com.example.honda_dealership.security.CustomUserDetails;
import com.example.honda_dealership.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public LoginResponse createTokenResponse(CustomUserDetails userDetails, User user, String deviceInfo, String ipAddress) {
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        saveRefreshToken(user.getId(), refreshToken, deviceInfo, ipAddress);

        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .role(user.getRole())
                .status(user.getStatus())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(jwtService.getAccessTokenExpiration() / 1000)
                .build();
    }

    @Transactional
    public RefreshTokenResponse refresh(String refreshTokenValue, String deviceInfo, String ipAddress) {
        validateRefreshToken(refreshTokenValue);

        String email = jwtService.extractUsername(refreshTokenValue);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new UnauthorizedException("User account is not active");
        }

        revokeRefreshToken(refreshTokenValue);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        saveRefreshToken(user.getId(), newRefreshToken, deviceInfo, ipAddress);

        return RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(jwtService.getAccessTokenExpiration() / 1000)
                .build();
    }

    @Transactional
    public void logout(String refreshTokenValue) {
        validateRefreshToken(refreshTokenValue);
        revokeRefreshToken(refreshTokenValue);
    }

    @Transactional
    public void logoutAll(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        refreshTokenRepository.revokeAllByUserId(user.getId());
    }

    public boolean isValidRefreshToken(String refreshTokenValue) {
        try {
            if (!jwtService.isRefreshToken(refreshTokenValue)) {
                return false;
            }

            String tokenHash = hashToken(refreshTokenValue);
            Optional<RefreshToken> storedToken = refreshTokenRepository.findByTokenHash(tokenHash);

            if (storedToken.isPresent()) {
                RefreshToken rt = storedToken.get();
                return rt.getRevoked() == false && !rt.isExpired();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateRefreshToken(String refreshTokenValue) {
        try {
            if (!jwtService.isRefreshToken(refreshTokenValue)) {
                throw new UnauthorizedException("Invalid token type");
            }

            if (jwtService.isTokenExpired(refreshTokenValue)) {
                throw new UnauthorizedException("Token expired");
            }

            String tokenHash = hashToken(refreshTokenValue);
            RefreshToken storedToken = refreshTokenRepository.findByTokenHash(tokenHash)
                    .orElseThrow(() -> new UnauthorizedException("Token not found"));

            if (storedToken.getRevoked()) {
                throw new UnauthorizedException("Token already revoked");
            }

            if (storedToken.isExpired()) {
                throw new UnauthorizedException("Token expired");
            }
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    private void saveRefreshToken(Long userId, String refreshToken, String deviceInfo, String ipAddress) {
        String tokenHash = hashToken(refreshToken);

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .userId(userId)
                .tokenHash(tokenHash)
                .expiresAt(LocalDateTime.now().plusNanos(jwtService.getRefreshTokenExpiration() * 1_000_000))
                .deviceInfo(deviceInfo)
                .ipAddress(ipAddress)
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);
    }

    private void revokeRefreshToken(String refreshTokenValue) {
        String tokenHash = hashToken(refreshTokenValue);
        refreshTokenRepository.revokeByTokenHash(tokenHash);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash token", e);
        }
    }
}