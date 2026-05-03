package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.request.UpdateSiteConfigRequest;
import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.SiteConfigResponse;
import com.example.honda_dealership.service.SiteConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/site-config")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    @GetMapping
    public ResponseEntity<ApiResponse<SiteConfigResponse>> getConfig() {
        return ResponseEntity.ok(ApiResponse.success(siteConfigService.getConfig()));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<SiteConfigResponse>> updateConfig(
            @Valid @RequestBody UpdateSiteConfigRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(siteConfigService.updateConfig(request)));
    }
}