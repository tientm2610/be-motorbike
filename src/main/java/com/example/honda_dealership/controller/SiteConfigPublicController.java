package com.example.honda_dealership.controller;

import com.example.honda_dealership.dto.response.ApiResponse;
import com.example.honda_dealership.dto.response.SiteConfigResponse;
import com.example.honda_dealership.service.SiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SiteConfigPublicController {

    private final SiteConfigService siteConfigService;

    @GetMapping("/site-config")
    public ResponseEntity<ApiResponse<SiteConfigResponse>> getSiteConfig() {
        return ResponseEntity.ok(ApiResponse.success(siteConfigService.getConfig()));
    }
}