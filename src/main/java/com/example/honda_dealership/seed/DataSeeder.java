package com.example.honda_dealership.seed;

import com.example.honda_dealership.config.SeedConfig;
import com.example.honda_dealership.entity.Brand;
import com.example.honda_dealership.entity.Category;
import com.example.honda_dealership.entity.Motorcycle;
import com.example.honda_dealership.entity.MotorcycleVariant;
import com.example.honda_dealership.entity.SiteConfig;
import com.example.honda_dealership.entity.User;
import com.example.honda_dealership.entity.VariantImage;
import com.example.honda_dealership.entity.enums.MotorcycleStatus;
import com.example.honda_dealership.entity.enums.UserRole;
import com.example.honda_dealership.entity.enums.UserStatus;
import com.example.honda_dealership.entity.enums.VariantStatus;
import com.example.honda_dealership.repository.BrandRepository;
import com.example.honda_dealership.repository.CategoryRepository;
import com.example.honda_dealership.repository.MotorcycleRepository;
import com.example.honda_dealership.repository.MotorcycleVariantRepository;
import com.example.honda_dealership.repository.SiteConfigRepository;
import com.example.honda_dealership.repository.UserRepository;
import com.example.honda_dealership.repository.VariantImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    private final VariantImageRepository variantImageRepository;
    private final SiteConfigRepository siteConfigRepository;
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
        seedSiteConfig();

        log.info("[SEED] Data seeding completed!");
    }

    private void seedSiteConfig() {
        if (siteConfigRepository.count() > 0) {
            log.info("[SEED] SiteConfig skipped (already exists)");
            return;
        }

        SiteConfig siteConfig = SiteConfig.builder()
                .shopName("Honda Dealership")
                .primaryColor("#e31837")
                .secondaryColor("#ffffff")
                .heroTitle("Ride Your Dream Bike")
                .heroSubtitle("Discover premium Honda motorcycles with expert guidance, competitive pricing, and unparalleled service.")
                .ctaPrimaryText("Khám phá xe máy")
                .ctaPrimaryLink("/motorcycles")
                .ctaSecondaryText("Tìm hiểu thêm")
                .ctaSecondaryLink("/about")
                .build();
        siteConfigRepository.save(siteConfig);
        log.info("[SEED] SiteConfig created");
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

        Motorcycle vision = createMotorcycle("Honda Vision 2026", "HV-2026", "honda-vision-2026",
                honda, scooter, "Honda Vision 2026 - Stylish and efficient scooter with modern design");
        createVariant(vision, "HV-VISION-RED", "Standard Red", "Red", "#FF0000", new BigDecimal("36000000"), 10);
        createVariant(vision, "HV-VISION-WHT", "Standard White", "White", "#FFFFFF", new BigDecimal("36000000"), 8);
        createVariant(vision, "HV-VISION-BLK", "Standard Black", "Black", "#000000", new BigDecimal("36500000"), 5);

        Motorcycle winner = createMotorcycle("Honda Winner X", "HW-WINNER", "honda-winner-x",
                honda, sport, "Honda Winner X - Sport Edition with aggressive styling");
        createVariant(winner, "HW-WINNER-RED", "Sport Red", "Red Racing", "#CC0000", new BigDecimal("48000000"), 12);
        createVariant(winner, "HW-WINNER-BLK", "Matt Black", "Black Matt", "#1A1A1A", new BigDecimal("48000000"), 6);

        Motorcycle grande = createMotorcycle("Yamaha Grande 2026", "YG-GRANDE", "yamaha-grande-2026",
                yamaha, scooter, "Yamaha Grande - Elegant and premium scooter");
        createVariant(grande, "YG-GRANDE-CRM", "Premium Cream", "Cream", "#FFFDD0", new BigDecimal("42000000"), 15);
        createVariant(grande, "YG-GRANDE-GRY", "Premium Gray", "Gray", "#808080", new BigDecimal("42000000"), 10);
        createVariant(grande, "YG-GRANDE-BLK", "Premium Black", "Black", "#1A1A1A", new BigDecimal("43000000"), 8);

        log.info("[SEED] Motorcycles created: {}", motorcycleRepository.count());
    }

    private Motorcycle createMotorcycle(String name, String code, String slug, Brand brand, Category category, String description) {
        Motorcycle motorcycle = Motorcycle.builder()
                .name(name)
                .code(code)
                .slug(slug)
                .brand(brand)
                .category(category)
                .status(MotorcycleStatus.ACTIVE)
                .description(description)
                .build();

        return motorcycleRepository.save(motorcycle);
    }

    private void createVariant(Motorcycle motorcycle, String sku, String variantName, String colorName, String colorCode, BigDecimal price, int stock) {
        MotorcycleVariant variant = MotorcycleVariant.builder()
                .motorcycle(motorcycle)
                .sku(sku)
                .variantName(variantName)
                .colorName(colorName)
                .colorCode(colorCode)
                .price(price)
                .stockQuantity(stock)
                .status(VariantStatus.AVAILABLE)
                .build();

        variant = variantRepository.save(variant);

        createVariantImages(variant, sku);
    }

    private void createVariantImages(MotorcycleVariant variant, String sku) {
        String baseUrl = "https://example.com/images/" + sku;

        VariantImage image1 = VariantImage.builder()
                .variant(variant)
                .imageUrl(baseUrl + "-1.jpg")
                .publicId(sku + "-1")
                .sortOrder(1)
                .isThumbnail(true)
                .build();
        variantImageRepository.save(image1);

        VariantImage image2 = VariantImage.builder()
                .variant(variant)
                .imageUrl(baseUrl + "-2.jpg")
                .publicId(sku + "-2")
                .sortOrder(2)
                .isThumbnail(false)
                .build();
        variantImageRepository.save(image2);

        VariantImage image3 = VariantImage.builder()
                .variant(variant)
                .imageUrl(baseUrl + "-3.jpg")
                .publicId(sku + "-3")
                .sortOrder(3)
                .isThumbnail(false)
                .build();
        variantImageRepository.save(image3);
    }
}