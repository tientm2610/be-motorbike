package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.VariantImageResponse;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.VariantImage;
import com.example.honda_dealership.mapper.ProductMapper;
import com.example.honda_dealership.repository.MotorcycleVariantRepository;
import com.example.honda_dealership.repository.VariantImageRepository;
import com.example.honda_dealership.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
public class AdminImageController {

    private final FileUploadService fileUploadService;
    private final VariantImageRepository variantImageRepository;
    private final MotorcycleVariantRepository variantRepository;
    private final ProductMapper productMapper;

    @PostMapping(value = "/variants/{variantId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VariantImageResponse>> uploadImage(
            @PathVariable Long variantId,
            @RequestParam("file") MultipartFile file
    ) {
        MotorcycleVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new com.example.honda_dealership.exception.ResourceNotFoundException(
                        "Variant not found with id: " + variantId));

        Map<String, String> uploadResult = fileUploadService.uploadImage(file);

        int nextSortOrder = variantImageRepository.findFirstByVariantIdOrderBySortOrderDesc(variantId)
                .map(img -> img.getSortOrder() + 1)
                .orElse(1);

        VariantImage variantImage = VariantImage.builder()
                .variant(variant)
                .imageUrl(uploadResult.get("secure_url"))
                .publicId(uploadResult.get("public_id"))
                .sortOrder(nextSortOrder)
                .isThumbnail(false)
                .build();

        variantImage = variantImageRepository.save(variantImage);

        log.info("Image uploaded for variant {}: {}", variantId, uploadResult.get("secure_url"));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(productMapper.toVariantImageResponse(variantImage)));
    }

    @PutMapping("/images/{imageId}/thumbnail")
    public ResponseEntity<ApiResponse<VariantImageResponse>> setThumbnail(@PathVariable Long imageId) {
        VariantImage image = variantImageRepository.findById(imageId)
                .orElseThrow(() -> new com.example.honda_dealership.exception.ResourceNotFoundException(
                        "Image not found with id: " + imageId));

        Long variantId = image.getVariant().getId();

        variantImageRepository.findByVariantIdAndIsThumbnailTrue(variantId)
                .ifPresent(oldThumbnail -> {
                    oldThumbnail.setIsThumbnail(false);
                    variantImageRepository.save(oldThumbnail);
                });

        image.setIsThumbnail(true);
        image = variantImageRepository.save(image);

        log.info("Thumbnail set for image {}", imageId);

        return ResponseEntity.ok(ApiResponse.success(productMapper.toVariantImageResponse(image)));
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable Long imageId) {
        VariantImage image = variantImageRepository.findById(imageId)
                .orElseThrow(() -> new com.example.honda_dealership.exception.ResourceNotFoundException(
                        "Image not found with id: " + imageId));

        fileUploadService.deleteImage(image.getPublicId());
        variantImageRepository.delete(image);

        log.info("Image deleted: {}", imageId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}