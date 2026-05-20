package com.example.honda_dealership.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.seed")
public class SeedConfig {

    private AdminSeed admin = new AdminSeed();
    private StaffSeed staff = new StaffSeed();

    @Data
    public static class AdminSeed {
        @Value("${SEED_ADMIN_EMAIL:admin@honda.com}")
        private String email;

        @Value("${SEED_ADMIN_PASSWORD:Admin@123}")
        private String password;
    }

    @Data
    public static class StaffSeed {
        @Value("${SEED_STAFF_EMAIL:staff@honda.com}")
        private String email;

        @Value("${SEED_STAFF_PASSWORD:Staff@123}")
        private String password;
    }
}