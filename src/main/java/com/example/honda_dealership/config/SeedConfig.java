package com.example.honda_dealership.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.seed")
public class SeedConfig {

    private AdminSeed admin = new AdminSeed();
    private StaffSeed staff = new StaffSeed();

    @Data
    public static class AdminSeed {
        private String email = "admin@honda.com";
        private String password = "Admin@123";
    }

    @Data
    public static class StaffSeed {
        private String email = "staff@honda.com";
        private String password = "Staff@123";
    }
}