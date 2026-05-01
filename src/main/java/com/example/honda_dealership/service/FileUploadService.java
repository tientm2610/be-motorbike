package com.example.honda_dealership.service;

import com.cloudinary.Cloudinary;
import com.example.honda_dealership.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/webp"
    );
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "jpg",
            "jpeg",
            "png",
            "webp"
    );

    private final Cloudinary cloudinary;

    public Map<String, String> uploadImage(MultipartFile file) {
        validateFile(file);

        try {
            String publicId = "motorcycle_" + UUID.randomUUID().toString().replace("-", "");

            Map<String, Object> params = new HashMap<>();
            params.put("public_id", publicId);
            params.put("overwrite", true);
            params.put("resource_type", "image");

            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            String secureUrl = (String) uploadResult.get("secure_url");
            String resultPublicId = (String) uploadResult.get("public_id");

            log.info("Image uploaded successfully: {}", secureUrl);

            Map<String, String> result = new HashMap<>();
            result.put("secure_url", secureUrl);
            result.put("public_id", resultPublicId);
            return result;
        } catch (IOException e) {
            log.error("Failed to upload image to Cloudinary", e);
            throw new BadRequestException("Failed to upload image: " + e.getMessage());
        }
    }

    public void deleteImage(String publicId) {
        if (publicId == null || publicId.isBlank()) {
            return;
        }

        try {
            cloudinary.uploader().destroy(publicId, null);
            log.info("Image deleted from Cloudinary: {}", publicId);
        } catch (IOException e) {
            log.error("Failed to delete image from Cloudinary: {}", publicId, e);
            throw new BadRequestException("Failed to delete image: " + e.getMessage());
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("File size must not exceed 5MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new BadRequestException("Invalid file type. Allowed: jpg, jpeg, png, webp");
        }

        String filename = file.getOriginalFilename();
        if (filename != null) {
            String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                throw new BadRequestException("Invalid file extension. Allowed: jpg, jpeg, png, webp");
            }
        }
    }
}