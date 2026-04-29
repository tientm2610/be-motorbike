package com.example.honda_dealership.seed;

import com.example.honda_dealership.config.SeedConfig;
import com.example.honda_dealership.entity.Brand;
import com.example.honda_dealership.entity.Category;
import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.User;
import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import com.example.honda_dealership.entity.enums.UserRole;
import com.example.honda_dealership.entity.enums.UserStatus;
import com.example.honda_dealership.entity.enums.VariantStatus;
import com.example.honda_dealership.repository.BrandRepository;
import com.example.honda_dealership.repository.CategoryRepository;
import com.example.honda_dealership.repository.MotorcycleRepository;
import com.example.honda_dealership.repository.MotorcycleVariantRepository;
import com.example.honda_dealership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleVariantRepository variantRepository;
    private final PasswordEncoder passwordEncoder;
    private final SeedConfig seedConfig;

    @Override
    public void run(String... args) {
        log.info("[SEED] Starting data seeding...");

        seedAdminUser();
        seedStaffUser();
        seedBrands();
        seedCategories();
        seedMotorcycles();

        log.info("[SEED] Data seeding completed!");
    }

    private void seedAdminUser() {
        Optional<User> existingAdmin = userRepository.findByEmail(seedConfig.getAdmin().getEmail());
        if (existingAdmin.isEmpty()) {
            User admin = User.builder()
                    .email(seedConfig.getAdmin().getEmail())
                    .passwordHash(passwordEncoder.encode(seedConfig.getAdmin().getPassword()))
                    .fullName("System Admin")
                    .phone("0123456789")
                    .role(UserRole.ADMIN)
                    .status(UserStatus.ACTIVE)
                    .build();
            userRepository.save(admin);
            log.info("[SEED] Admin account created: {}", seedConfig.getAdmin().getEmail());
        } else {
            log.info("[SEED] Admin account skipped (already exists)");
        }
    }

    private void seedStaffUser() {
        Optional<User> existingStaff = userRepository.findByEmail(seedConfig.getStaff().getEmail());
        if (existingStaff.isEmpty()) {
            User staff = User.builder()
                    .email(seedConfig.getStaff().getEmail())
                    .passwordHash(passwordEncoder.encode(seedConfig.getStaff().getPassword()))
                    .fullName("Staff Member")
                    .phone("0123456788")
                    .role(UserRole.STAFF)
                    .status(UserStatus.ACTIVE)
                    .build();
            userRepository.save(staff);
            log.info("[SEED] Staff account created: {}", seedConfig.getStaff().getEmail());
        } else {
            log.info("[SEED] Staff account skipped (already exists)");
        }
    }

    private void seedBrands() {
        List<String> brandNames = List.of("Honda", "Yamaha", "Suzuki", "Kawasaki", "Piaggio");

        long existingCount = brandRepository.count();
        if (existingCount > 0) {
            log.info("[SEED] Brands skipped ({} already exist)", existingCount);
            return;
        }

        for (String name : brandNames) {
            Brand brand = Brand.builder()
                    .name(name)
                    .description(name + " motorcycle brand")
                    .build();
            brandRepository.save(brand);
        }
        log.info("[SEED] Brands created: {}", brandNames.size());
    }

    private void seedCategories() {
        List<String> categoryNames = List.of("Scooter", "Sport", "Naked", "Cruiser", "Adventure", "Electric");

        long existingCount = categoryRepository.count();
        if (existingCount > 0) {
            log.info("[SEED] Categories skipped ({} already exist)", existingCount);
            return;
        }

        for (String name : categoryNames) {
            String slug = name.toLowerCase();
            Category category = Category.builder()
                    .name(name)
                    .slug(slug)
                    .description(name + " type motorcycle")
                    .build();
            categoryRepository.save(category);
        }
        log.info("[SEED] Categories created: {}", categoryNames.size());
    }

    private void seedMotorcycles() {
        long existingCount = motorcycleRepository.count();
        if (existingCount > 0) {
            log.info("[SEED] Motorcycles skipped ({} already exist)", existingCount);
            return;
        }

        List<Brand> brands = brandRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        if (brands.isEmpty() || categories.isEmpty()) {
            log.warn("[SEED] Cannot seed motorcycles - brands or categories not found");
            return;
        }

        Brand honda = brands.stream().filter(b -> b.getName().equals("Honda")).findFirst().orElse(brands.get(0));
        Brand yamaha = brands.stream().filter(b -> b.getName().equals("Yamaha")).findFirst().orElse(brands.get(0));

        Category scooter = categories.stream().filter(c -> c.getName().equals("Scooter")).findFirst().orElse(categories.get(0));
        Category sport = categories.stream().filter(c -> c.getName().equals("Sport")).findFirst().orElse(categories.get(0));

        // Motorcycle 1: Honda Vision
        Motorcycle vision = createMotorcycle("Honda Vision 2026", "HV-2026", "honda-vision-2026",
                new BigDecimal("35000000"), honda, scooter,
                "Honda Vision 2026 - Stylish and efficient scooter with modern design");
        createVariant(vision, "HV-2026-RED", "Red", "#FF0000", new BigDecimal("500000"), 10);
        createVariant(vision, "HV-2026-WHT", "White", "#FFFFFF", new BigDecimal("500000"), 8);
        createVariant(vision, "HV-2026-BLK", "Black", "#000000", new BigDecimal("500000"), 5);

        // Motorcycle 2: Honda Winner
        Motorcycle winner = createMotorcycle("Honda Winner X", "HW-WINNER", "honda-winner-x",
                new BigDecimal("48000000"), honda, sport,
                "Honda Winner X - Sport Edition with aggressive styling");
        createVariant(winner, "HW-WINNER-RED", "Red Racing", "#CC0000", new BigDecimal("0"), 12);
        createVariant(winner, "HW-WINNER-BLK", "Black Matt", "#1A1A1A", new BigDecimal("0"), 6);

        // Motorcycle 3: Yamaha Grande
        Motorcycle grande = createMotorcycle("Yamaha Grande 2026", "YG-GRANDE", "yamaha-grande-2026",
                new BigDecimal("38000000"), yamaha, scooter,
                "Yamaha Grande - Elegant and premium scooter");
        createVariant(grande, "YG-GRANDE-CRM", "Cream", "#FFFDD0", new BigDecimal("300000"), 15);
        createVariant(grande, "YG-GRANDE-GRY", "Gray", "#808080", new BigDecimal("300000"), 10);

        // Motorcycle 4: Yamaha Exciter
        Motorcycle exciter = createMotorcycle("Yamaha Exciter 155", "YE-EXCITER", "yamaha-exciter-155",
                new BigDecimal("55000000"), yamaha, sport,
                "Yamaha Exciter 155 - High performance sport bike");
        createVariant(exciter, "YE-EXCITER-BLK", "Black", "#000000", new BigDecimal("0"), 8);
        createVariant(exciter, "YE-EXCITER-WHT", "White", "#FFFFFF", new BigDecimal("0"), 5);
        createVariant(exciter, "YE-EXCITER-RED", "Red", "#FF0000", new BigDecimal("1000000"), 3);

        // Motorcycle 5: Honda SH
        Motorcycle sh = createMotorcycle("Honda SH 125i", "HSH-125", "honda-sh-125i",
                new BigDecimal("65000000"), honda, scooter,
                "Honda SH 125i - Premium urban scooter with advanced features");
        createVariant(sh, "HSH-125-SLV", "Silver", "#C0C0C0", new BigDecimal("1000000"), 7);
        createVariant(sh, "HSH-125-BLK", "Black", "#1A1A1A", new BigDecimal("1000000"), 4);

        log.info("[SEED] Motorcycles created: {}", motorcycleRepository.count());
    }

    private Motorcycle createMotorcycle(String name, String code, String slug, BigDecimal price, Brand brand, Category category, String description) {
        Motorcycle motorcycle = Motorcycle.builder()
                .name(name)
                .code(code)
                .slug(slug)
                .basePrice(price)
                .brand(brand)
                .status(MotorcycleStatus.ACTIVE)
                .description(description)
                .thumbnailUrl("https://example.com/images/" + slug + ".jpg")
                .build();

        motorcycle.setCategories(new HashSet<>(List.of(category)));
        return motorcycleRepository.save(motorcycle);
    }

    private void createVariant(Motorcycle motorcycle, String sku, String colorName, String colorCode, BigDecimal extraPrice, int stock) {
        MotorcycleVariant variant = MotorcycleVariant.builder()
                .motorcycle(motorcycle)
                .sku(sku)
                .variantName(colorName)
                .colorName(colorName)
                .colorCode(colorCode)
                .extraPrice(extraPrice)
                .stockQuantity(stock)
                .status(VariantStatus.AVAILABLE)
                .imageUrl("https://example.com/images/" + sku + ".jpg")
                .build();
        variantRepository.save(variant);
    }
}