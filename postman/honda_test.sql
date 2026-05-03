/*
 Navicat Premium Data Transfer

 Source Server         : but
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : honda_test

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 03/05/2026 19:16:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for brands
-- ----------------------------
DROP TABLE IF EXISTS `brands`;
CREATE TABLE `brands`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `logo_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brands
-- ----------------------------
INSERT INTO `brands` VALUES (1, '2026-05-03 18:12:07.362238', 'Honda motorcycle brand', NULL, 'Honda', '2026-05-03 18:12:07.362238');
INSERT INTO `brands` VALUES (2, '2026-05-03 18:12:07.366237', 'Yamaha motorcycle brand', NULL, 'Yamaha', '2026-05-03 18:12:07.366237');
INSERT INTO `brands` VALUES (3, '2026-05-03 18:12:07.371332', 'Suzuki motorcycle brand', NULL, 'Suzuki', '2026-05-03 18:12:07.371332');
INSERT INTO `brands` VALUES (4, '2026-05-03 18:12:07.376030', 'Kawasaki motorcycle brand', NULL, 'Kawasaki', '2026-05-03 18:12:07.376030');
INSERT INTO `brands` VALUES (5, '2026-05-03 18:12:07.381035', 'Piaggio motorcycle brand', NULL, 'Piaggio', '2026-05-03 18:12:07.381035');

-- ----------------------------
-- Table structure for cart_items
-- ----------------------------
DROP TABLE IF EXISTS `cart_items`;
CREATE TABLE `cart_items`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `quantity` int NOT NULL,
  `session_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `unit_price_snapshot` decimal(12, 2) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `variant_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_cart_items_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_cart_items_variant_id`(`variant_id` ASC) USING BTREE,
  CONSTRAINT `FK5wx9udlf8twl1r7d6engnkuqj` FOREIGN KEY (`variant_id`) REFERENCES `motorcycle_variants` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK709eickf3kc0dujx3ub9i7btf` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart_items
-- ----------------------------

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `slug` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKoul14ho7bctbefv8jywp5v3i2`(`slug` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, '2026-05-03 18:12:07.392229', 'Scooter type motorcycle', 'Scooter', 'scooter', '2026-05-03 18:12:07.392229');
INSERT INTO `categories` VALUES (2, '2026-05-03 18:12:07.396229', 'Sport type motorcycle', 'Sport', 'sport', '2026-05-03 18:12:07.396229');
INSERT INTO `categories` VALUES (3, '2026-05-03 18:12:07.401542', 'Naked type motorcycle', 'Naked', 'naked', '2026-05-03 18:12:07.401542');
INSERT INTO `categories` VALUES (4, '2026-05-03 18:12:07.405293', 'Cruiser type motorcycle', 'Cruiser', 'cruiser', '2026-05-03 18:12:07.405293');
INSERT INTO `categories` VALUES (5, '2026-05-03 18:12:07.409292', 'Adventure type motorcycle', 'Adventure', 'adventure', '2026-05-03 18:12:07.409292');
INSERT INTO `categories` VALUES (6, '2026-05-03 18:12:07.414293', 'Electric type motorcycle', 'Electric', 'electric', '2026-05-03 18:12:07.414293');

-- ----------------------------
-- Table structure for motorcycle_variants
-- ----------------------------
DROP TABLE IF EXISTS `motorcycle_variants`;
CREATE TABLE `motorcycle_variants`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `color_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `price` decimal(12, 2) NOT NULL,
  `sku` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` enum('AVAILABLE','DISCONTINUED','OUT_OF_STOCK') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `stock_quantity` int NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `variant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `motorcycle_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_motorcycle_variants_sku`(`sku` ASC) USING BTREE,
  INDEX `idx_motorcycle_variants_motorcycle_id`(`motorcycle_id` ASC) USING BTREE,
  CONSTRAINT `FKk4sbt8wcffpt1urmc2wwpnwcp` FOREIGN KEY (`motorcycle_id`) REFERENCES `motorcycles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of motorcycle_variants
-- ----------------------------
INSERT INTO `motorcycle_variants` VALUES (1, '#E31837', 'Red', '2026-05-03 18:12:07.455512', 36000000.00, 'HV-VISION-RED', 'AVAILABLE', 15, '2026-05-03 18:12:07.455512', 'Standard Red', 1);
INSERT INTO `motorcycle_variants` VALUES (2, '#FFFFFF', 'White', '2026-05-03 18:12:07.476134', 36000000.00, 'HV-VISION-WHT', 'AVAILABLE', 10, '2026-05-03 18:12:07.476134', 'Standard White', 1);
INSERT INTO `motorcycle_variants` VALUES (3, '#0066CC', 'Blue', '2026-05-03 18:12:07.497243', 55000000.00, 'SH-MODE-VNG', 'AVAILABLE', 12, '2026-05-03 18:12:07.497243', 'Vietnam Edition', 2);
INSERT INTO `motorcycle_variants` VALUES (4, '#CC0000', 'Red', '2026-05-03 18:12:07.514643', 58000000.00, 'SH-MODE-RED', 'AVAILABLE', 8, '2026-05-03 18:12:07.514643', 'Sport Red', 2);
INSERT INTO `motorcycle_variants` VALUES (5, '#1E90FF', 'Blue', '2026-05-03 18:12:07.535732', 45000000.00, 'AIR-BLADE-XANH', 'AVAILABLE', 10, '2026-05-03 18:12:07.535732', 'Xanh Lam', 3);
INSERT INTO `motorcycle_variants` VALUES (6, '#FF0000', 'Red', '2026-05-03 18:12:07.552592', 45000000.00, 'AIR-BLADE-DO', 'AVAILABLE', 8, '2026-05-03 18:12:07.552592', 'Do Mau', 3);
INSERT INTO `motorcycle_variants` VALUES (7, '#E31837', 'Red', '2026-05-03 18:12:07.576004', 65000000.00, 'PCX-DO', 'AVAILABLE', 10, '2026-05-03 18:12:07.576004', 'Red', 4);
INSERT INTO `motorcycle_variants` VALUES (8, '#1E90FF', 'Blue', '2026-05-03 18:12:07.593069', 65000000.00, 'PCX-XANH', 'AVAILABLE', 8, '2026-05-03 18:12:07.593069', 'Blue', 4);
INSERT INTO `motorcycle_variants` VALUES (9, '#800080', 'Purple', '2026-05-03 18:12:07.612717', 42000000.00, 'LEAD-TIM', 'AVAILABLE', 8, '2026-05-03 18:12:07.612717', 'Tim', 5);
INSERT INTO `motorcycle_variants` VALUES (10, '#E31837', 'Red', '2026-05-03 18:12:07.632057', 42000000.00, 'LEAD-DO', 'AVAILABLE', 8, '2026-05-03 18:12:07.632057', 'Do', 5);
INSERT INTO `motorcycle_variants` VALUES (11, '#CC0000', 'Racing Red', '2026-05-03 18:12:07.656986', 48000000.00, 'WINNER-DO', 'AVAILABLE', 12, '2026-05-03 18:12:07.656986', 'Do Dang', 6);
INSERT INTO `motorcycle_variants` VALUES (12, '#2D2D2D', 'Black', '2026-05-03 18:12:07.673891', 48000000.00, 'WINNER-DEN', 'AVAILABLE', 8, '2026-05-03 18:12:07.673891', 'Den Mat', 6);
INSERT INTO `motorcycle_variants` VALUES (13, '#FF0000', 'Red', '2026-05-03 18:12:07.695775', 150000000.00, 'CBR-RED', 'AVAILABLE', 5, '2026-05-03 18:12:07.695775', 'Red', 7);
INSERT INTO `motorcycle_variants` VALUES (14, '#1A1A1A', 'Black', '2026-05-03 18:12:07.713836', 150000000.00, 'CBR-BLK', 'AVAILABLE', 3, '2026-05-03 18:12:07.713836', 'Black', 7);
INSERT INTO `motorcycle_variants` VALUES (15, '#D32F2F', 'Red', '2026-05-03 18:12:07.737028', 180000000.00, 'CB650R-RED', 'AVAILABLE', 4, '2026-05-03 18:12:07.737028', 'Red', 8);
INSERT INTO `motorcycle_variants` VALUES (16, '#424242', 'Black', '2026-05-03 18:12:07.756381', 180000000.00, 'CB650R-BLK', 'AVAILABLE', 3, '2026-05-03 18:12:07.756381', 'Matte Black', 8);
INSERT INTO `motorcycle_variants` VALUES (17, '#D32F2F', 'Red', '2026-05-03 18:12:07.779411', 95000000.00, 'CB500F-RED', 'AVAILABLE', 5, '2026-05-03 18:12:07.779411', 'Red', 9);
INSERT INTO `motorcycle_variants` VALUES (18, '#F5F5F5', 'White', '2026-05-03 18:12:07.795465', 95000000.00, 'CB500F-WHT', 'AVAILABLE', 4, '2026-05-03 18:12:07.795465', 'White', 9);
INSERT INTO `motorcycle_variants` VALUES (19, '#FFF', 'White/Red', '2026-05-03 18:12:07.818359', 95000000.00, 'CRF-WRN', 'AVAILABLE', 6, '2026-05-03 18:12:07.818359', 'White Red', 10);
INSERT INTO `motorcycle_variants` VALUES (20, '#000', 'Black', '2026-05-03 18:12:07.833066', 95000000.00, 'CRF-BLK', 'AVAILABLE', 4, '2026-05-03 18:12:07.833066', 'Black', 10);
INSERT INTO `motorcycle_variants` VALUES (21, '#FFFDD0', 'Cream', '2026-05-03 18:12:07.852955', 42000000.00, 'YG-GRANDE-CRM', 'AVAILABLE', 15, '2026-05-03 18:12:07.852955', 'Premium Cream', 11);
INSERT INTO `motorcycle_variants` VALUES (22, '#A9A9A9', 'Gray', '2026-05-03 18:12:07.868332', 42000000.00, 'YG-GRANDE-GRY', 'AVAILABLE', 10, '2026-05-03 18:12:07.868332', 'Premium Gray', 11);
INSERT INTO `motorcycle_variants` VALUES (23, '#800080', 'Purple', '2026-05-03 18:12:07.887820', 32000000.00, 'JANUS-TIM', 'AVAILABLE', 10, '2026-05-03 18:12:07.887820', 'Purple', 12);
INSERT INTO `motorcycle_variants` VALUES (24, '#E31837', 'Red', '2026-05-03 18:12:07.902627', 32000000.00, 'JANUS-DO', 'AVAILABLE', 8, '2026-05-03 18:12:07.902627', 'Red', 12);
INSERT INTO `motorcycle_variants` VALUES (25, '#FFD700', 'Gold', '2026-05-03 18:12:07.923820', 38000000.00, 'FX-GLD', 'AVAILABLE', 5, '2026-05-03 18:12:07.923820', 'Gold', 13);
INSERT INTO `motorcycle_variants` VALUES (26, '#C0C0C0', 'Silver', '2026-05-03 18:12:07.941151', 38000000.00, 'FX-SLV', 'AVAILABLE', 5, '2026-05-03 18:12:07.941151', 'Silver', 13);
INSERT INTO `motorcycle_variants` VALUES (27, '#CC0000', 'Red Black', '2026-05-03 18:12:07.961560', 48000000.00, 'EXCITER-DO', 'AVAILABLE', 10, '2026-05-03 18:12:07.961560', 'Do Den', 14);
INSERT INTO `motorcycle_variants` VALUES (28, '#1A1A1A', 'Black', '2026-05-03 18:12:07.975516', 48000000.00, 'EXCITER-DEN', 'AVAILABLE', 8, '2026-05-03 18:12:07.976526', 'Den', 14);
INSERT INTO `motorcycle_variants` VALUES (29, '#CC0000', 'Red', '2026-05-03 18:12:07.997024', 55000000.00, 'NVX-DO', 'AVAILABLE', 8, '2026-05-03 18:12:07.997024', 'Racing Red', 15);
INSERT INTO `motorcycle_variants` VALUES (30, '#0066CC', 'Blue', '2026-05-03 18:12:08.015753', 55000000.00, 'NVX-XANH', 'AVAILABLE', 6, '2026-05-03 18:12:08.015753', 'Blue', 15);
INSERT INTO `motorcycle_variants` VALUES (31, '#FF0000', 'Red', '2026-05-03 18:12:08.038135', 78000000.00, 'R15-V4-DO', 'AVAILABLE', 6, '2026-05-03 18:12:08.038135', 'Racing Red', 16);
INSERT INTO `motorcycle_variants` VALUES (32, '#0066CC', 'Blue', '2026-05-03 18:12:08.057758', 78000000.00, 'R15-V4-BLU', 'AVAILABLE', 4, '2026-05-03 18:12:08.057758', 'Electric Blue', 16);
INSERT INTO `motorcycle_variants` VALUES (33, '#212121', 'Black', '2026-05-03 18:12:08.081468', 68000000.00, 'MT15-DEN', 'AVAILABLE', 6, '2026-05-03 18:12:08.081468', 'Matte Black', 17);
INSERT INTO `motorcycle_variants` VALUES (34, '#228B22', 'Green', '2026-05-03 18:12:08.103131', 68000000.00, 'MT15-CAM', 'AVAILABLE', 4, '2026-05-03 18:12:08.103131', 'Camouflage Green', 17);
INSERT INTO `motorcycle_variants` VALUES (35, '#212121', 'Black', '2026-05-03 18:12:08.132338', 195000000.00, 'MT07-DEN', 'AVAILABLE', 4, '2026-05-03 18:12:08.132338', 'Matte Black', 18);
INSERT INTO `motorcycle_variants` VALUES (36, '#228B22', 'Green', '2026-05-03 18:12:08.147238', 195000000.00, 'MT07-CAM', 'AVAILABLE', 3, '2026-05-03 18:12:08.147238', 'Storm Green', 18);
INSERT INTO `motorcycle_variants` VALUES (37, '#D32F2F', 'Red', '2026-05-03 18:12:08.193930', 38000000.00, 'RAIDER-DO', 'AVAILABLE', 12, '2026-05-03 18:12:08.193930', 'M Red', 19);
INSERT INTO `motorcycle_variants` VALUES (38, '#1A1A1A', 'Black', '2026-05-03 18:12:08.207143', 38000000.00, 'RAIDER-DEN', 'AVAILABLE', 8, '2026-05-03 18:12:08.207143', 'Black', 19);
INSERT INTO `motorcycle_variants` VALUES (39, '#FF0000', 'Red', '2026-05-03 18:12:08.224466', 45000000.00, 'GSX-DO', 'AVAILABLE', 8, '2026-05-03 18:12:08.224466', 'Red', 20);
INSERT INTO `motorcycle_variants` VALUES (40, '#1A1A1A', 'Black', '2026-05-03 18:12:08.241224', 45000000.00, 'GSX-DEN', 'AVAILABLE', 6, '2026-05-03 18:12:08.241224', 'Black', 20);
INSERT INTO `motorcycle_variants` VALUES (41, '#9370DB', 'Purple', '2026-05-03 18:12:08.261187', 28000000.00, 'ADDRESS-TIM', 'AVAILABLE', 10, '2026-05-03 18:12:08.261187', 'Purple', 21);
INSERT INTO `motorcycle_variants` VALUES (42, '#0066CC', 'Blue', '2026-05-03 18:12:08.275489', 28000000.00, 'ADDRESS-XANH', 'AVAILABLE', 8, '2026-05-03 18:12:08.275489', 'Blue', 21);
INSERT INTO `motorcycle_variants` VALUES (43, '#2D2D2D', 'Black', '2026-05-03 18:12:08.295971', 85000000.00, 'BURGMAN-DEN', 'AVAILABLE', 5, '2026-05-03 18:12:08.295971', 'Metallic Black', 22);
INSERT INTO `motorcycle_variants` VALUES (44, '#C0C0C0', 'Silver', '2026-05-03 18:12:08.310167', 85000000.00, 'BURGMAN-BAC', 'AVAILABLE', 4, '2026-05-03 18:12:08.310167', 'Silver', 22);
INSERT INTO `motorcycle_variants` VALUES (45, '#32CD32', 'Green', '2026-05-03 18:12:08.329044', 165000000.00, 'N400-LIMO', 'AVAILABLE', 4, '2026-05-03 18:12:08.329044', 'Lime Green', 23);
INSERT INTO `motorcycle_variants` VALUES (46, '#212121', 'Black', '2026-05-03 18:12:08.343167', 165000000.00, 'N400-DEN', 'AVAILABLE', 3, '2026-05-03 18:12:08.343167', 'Matte Black', 23);
INSERT INTO `motorcycle_variants` VALUES (47, '#32CD32', 'Green', '2026-05-03 18:12:08.362215', 155000000.00, 'Z400-LIMO', 'AVAILABLE', 4, '2026-05-03 18:12:08.362215', 'Lime Green', 24);
INSERT INTO `motorcycle_variants` VALUES (48, '#212121', 'Black', '2026-05-03 18:12:08.377295', 155000000.00, 'Z400-DEN', 'AVAILABLE', 3, '2026-05-03 18:12:08.377295', 'Matte Black', 24);
INSERT INTO `motorcycle_variants` VALUES (49, '#F5F5F5', 'White', '2026-05-03 18:12:08.396927', 145000000.00, 'VERSYS-WRN', 'AVAILABLE', 3, '2026-05-03 18:12:08.396927', 'White', 25);
INSERT INTO `motorcycle_variants` VALUES (50, '#212121', 'Black', '2026-05-03 18:12:08.415439', 145000000.00, 'VERSYS-DEN', 'AVAILABLE', 2, '2026-05-03 18:12:08.415439', 'Black', 25);
INSERT INTO `motorcycle_variants` VALUES (51, '#CC0000', 'Red', '2026-05-03 18:12:08.433645', 95000000.00, 'SPRINT-DO', 'AVAILABLE', 4, '2026-05-03 18:12:08.433645', 'Red', 26);
INSERT INTO `motorcycle_variants` VALUES (52, '#F5F5F5', 'White', '2026-05-03 18:12:08.448242', 95000000.00, 'SPRINT-TRANG', 'AVAILABLE', 3, '2026-05-03 18:12:08.448242', 'White', 26);
INSERT INTO `motorcycle_variants` VALUES (53, '#800020', 'Purple', '2026-05-03 18:12:08.467373', 88000000.00, 'PRIMAVERA-TIM', 'AVAILABLE', 4, '2026-05-03 18:12:08.467373', 'Purple', 27);
INSERT INTO `motorcycle_variants` VALUES (54, '#F5F5F5', 'White', '2026-05-03 18:12:08.482376', 88000000.00, 'PRIMAVERA-TRANG', 'AVAILABLE', 3, '2026-05-03 18:12:08.482376', 'White', 27);
INSERT INTO `motorcycle_variants` VALUES (55, '#8A2BE2', 'Purple', '2026-05-03 18:12:08.506765', 45000000.00, 'LIBERTY-TIM', 'AVAILABLE', 6, '2026-05-03 18:12:08.506765', 'Purple', 28);
INSERT INTO `motorcycle_variants` VALUES (56, '#E31837', 'Red', '2026-05-03 18:12:08.521006', 45000000.00, 'LIBERTY-DO', 'AVAILABLE', 5, '2026-05-03 18:12:08.521006', 'Red', 28);

-- ----------------------------
-- Table structure for motorcycles
-- ----------------------------
DROP TABLE IF EXISTS `motorcycles`;
CREATE TABLE `motorcycles`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `slug` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `specs_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `status` enum('ACTIVE','INACTIVE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `brand_id` bigint NOT NULL,
  `category_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_motorcycles_slug`(`slug` ASC) USING BTREE,
  UNIQUE INDEX `UKsi0pjxtmumyynoeib813c0j0d`(`code` ASC) USING BTREE,
  INDEX `idx_motorcycles_name`(`name` ASC) USING BTREE,
  INDEX `idx_motorcycles_brand_id`(`brand_id` ASC) USING BTREE,
  INDEX `idx_motorcycles_category_id`(`category_id` ASC) USING BTREE,
  CONSTRAINT `FK1pbg9v95rit17vf0kju6i67po` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKebpyplufqdor1oriw8p96903t` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of motorcycles
-- ----------------------------
INSERT INTO `motorcycles` VALUES (1, 'HV-2026', '2026-05-03 18:12:07.448695', 'Vision 2026', 'Honda Vision 2026', 'honda-vision-2026', NULL, 'ACTIVE', '2026-05-03 18:12:07.448695', 1, 1);
INSERT INTO `motorcycles` VALUES (2, 'SH-MODE', '2026-05-03 18:12:07.492869', 'SH Mode', 'Honda SH Mode', 'honda-sh-mode', NULL, 'ACTIVE', '2026-05-03 18:12:07.492869', 1, 1);
INSERT INTO `motorcycles` VALUES (3, 'AIR-BLADE', '2026-05-03 18:12:07.531311', 'Air Blade', 'Honda Air Blade', 'honda-air-blade', NULL, 'ACTIVE', '2026-05-03 18:12:07.531311', 1, 1);
INSERT INTO `motorcycles` VALUES (4, 'PCX-2026', '2026-05-03 18:12:07.570356', 'PCX', 'Honda PCX', 'honda-pcx-2026', NULL, 'ACTIVE', '2026-05-03 18:12:07.570356', 1, 1);
INSERT INTO `motorcycles` VALUES (5, 'LEAD-2026', '2026-05-03 18:12:07.607888', 'Lead', 'Honda Lead', 'honda-lead-2026', NULL, 'ACTIVE', '2026-05-03 18:12:07.607888', 1, 1);
INSERT INTO `motorcycles` VALUES (6, 'HW-WINNER', '2026-05-03 18:12:07.650390', 'Winner X', 'Honda Winner X', 'honda-winner-x', NULL, 'ACTIVE', '2026-05-03 18:12:07.650390', 1, 2);
INSERT INTO `motorcycles` VALUES (7, 'CBR-500R', '2026-05-03 18:12:07.691245', 'CBR500R', 'Honda CBR500R', 'honda-cbr500r', NULL, 'ACTIVE', '2026-05-03 18:12:07.691245', 1, 2);
INSERT INTO `motorcycles` VALUES (8, 'CB-650R', '2026-05-03 18:12:07.731564', 'CB650R', 'Honda CB650R', 'honda-cb650r', NULL, 'ACTIVE', '2026-05-03 18:12:07.731564', 1, 3);
INSERT INTO `motorcycles` VALUES (9, 'CB-500F', '2026-05-03 18:12:07.775619', 'CB500F', 'Honda CB500F', 'honda-cb500f', NULL, 'ACTIVE', '2026-05-03 18:12:07.775619', 1, 3);
INSERT INTO `motorcycles` VALUES (10, 'CRF-300L', '2026-05-03 18:12:07.814168', 'CRF300L', 'Honda CRF300L', 'honda-crf300l', NULL, 'ACTIVE', '2026-05-03 18:12:07.814168', 1, 5);
INSERT INTO `motorcycles` VALUES (11, 'YG-GRANDE', '2026-05-03 18:12:07.849257', 'Grande', 'Yamaha Grande', 'yamaha-grande-2026', NULL, 'ACTIVE', '2026-05-03 18:12:07.849257', 2, 1);
INSERT INTO `motorcycles` VALUES (12, 'JANUS', '2026-05-03 18:12:07.883816', 'Janus', 'Yamaha Janus', 'yamaha-janus', NULL, 'ACTIVE', '2026-05-03 18:12:07.883816', 2, 1);
INSERT INTO `motorcycles` VALUES (13, 'FX-LIMITED', '2026-05-03 18:12:07.918637', 'FX Limited', 'Yamaha FX Limited', 'yamaha-fx-limited', NULL, 'ACTIVE', '2026-05-03 18:12:07.918637', 2, 1);
INSERT INTO `motorcycles` VALUES (14, 'EXCITER', '2026-05-03 18:12:07.957559', 'Exciter', 'Yamaha Exciter', 'yamaha-exciter', NULL, 'ACTIVE', '2026-05-03 18:12:07.957559', 2, 2);
INSERT INTO `motorcycles` VALUES (15, 'NVX-2026', '2026-05-03 18:12:07.991630', 'NVX', 'Yamaha NVX', 'yamaha-nvx-2026', NULL, 'ACTIVE', '2026-05-03 18:12:07.992630', 2, 2);
INSERT INTO `motorcycles` VALUES (16, 'R15-V4', '2026-05-03 18:12:08.034133', 'R15', 'Yamaha R15', 'yamaha-r15v4', NULL, 'ACTIVE', '2026-05-03 18:12:08.034133', 2, 2);
INSERT INTO `motorcycles` VALUES (17, 'MT-15', '2026-05-03 18:12:08.075363', 'MT15', 'Yamaha MT-15', 'yamaha-mt15', NULL, 'ACTIVE', '2026-05-03 18:12:08.075363', 2, 3);
INSERT INTO `motorcycles` VALUES (18, 'MT-07', '2026-05-03 18:12:08.129309', 'MT07', 'Yamaha MT-07', 'yamaha-mt07', NULL, 'ACTIVE', '2026-05-03 18:12:08.129309', 2, 3);
INSERT INTO `motorcycles` VALUES (19, 'RAIDER', '2026-05-03 18:12:08.190636', 'Raider', 'Suzuki Raider', 'suzuki-raider', NULL, 'ACTIVE', '2026-05-03 18:12:08.190636', 3, 2);
INSERT INTO `motorcycles` VALUES (20, 'GSX-150', '2026-05-03 18:12:08.220677', 'GSX150', 'Suzuki GSX150', 'suzuki-gsx150', NULL, 'ACTIVE', '2026-05-03 18:12:08.220677', 3, 3);
INSERT INTO `motorcycles` VALUES (21, 'ADDRESS', '2026-05-03 18:12:08.257187', 'Address', 'Suzuki Address', 'suzuki-address', NULL, 'ACTIVE', '2026-05-03 18:12:08.257187', 3, 1);
INSERT INTO `motorcycles` VALUES (22, 'BURGMAN', '2026-05-03 18:12:08.291575', 'Burgman', 'Suzuki Burgman', 'suzuki-burgman', NULL, 'ACTIVE', '2026-05-03 18:12:08.291575', 3, 1);
INSERT INTO `motorcycles` VALUES (23, 'NINJA-400', '2026-05-03 18:12:08.325041', 'Ninja 400', 'Kawasaki Ninja 400', 'kawasaki-ninja400', NULL, 'ACTIVE', '2026-05-03 18:12:08.325041', 4, 2);
INSERT INTO `motorcycles` VALUES (24, 'Z-400', '2026-05-03 18:12:08.358214', 'Z400', 'Kawasaki Z400', 'kawasaki-z400', NULL, 'ACTIVE', '2026-05-03 18:12:08.358214', 4, 3);
INSERT INTO `motorcycles` VALUES (25, 'VERSYS-300', '2026-05-03 18:12:08.392673', 'Versys 300', 'Kawasaki Versys', 'kawasaki-versys300', NULL, 'ACTIVE', '2026-05-03 18:12:08.392673', 4, 5);
INSERT INTO `motorcycles` VALUES (26, 'VESPA-SPRINT', '2026-05-03 18:12:08.429253', 'Vespa Sprint', 'Vespa Sprint', 'vespa-sprint', NULL, 'ACTIVE', '2026-05-03 18:12:08.429253', 5, 1);
INSERT INTO `motorcycles` VALUES (27, 'VESPA-PRIMAVERA', '2026-05-03 18:12:08.463467', 'Vespa Primavera', 'Vespa Primavera', 'vespa-primavera', NULL, 'ACTIVE', '2026-05-03 18:12:08.463467', 5, 1);
INSERT INTO `motorcycles` VALUES (28, 'LIBERTY', '2026-05-03 18:12:08.501764', 'Liberty', 'Piaggio Liberty', 'piaggio-liberty', NULL, 'ACTIVE', '2026-05-03 18:12:08.501764', 5, 1);

-- ----------------------------
-- Table structure for order_items
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `product_name_snapshot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `quantity` int NOT NULL,
  `subtotal` decimal(12, 2) NOT NULL,
  `unit_price` decimal(12, 2) NOT NULL,
  `variant_name_snapshot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `motorcycle_id` bigint NOT NULL,
  `order_id` bigint NOT NULL,
  `variant_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKjddr6axxh7wxn50sblx69nx4e`(`motorcycle_id` ASC) USING BTREE,
  INDEX `FKbioxgbv59vetrxe0ejfubep1w`(`order_id` ASC) USING BTREE,
  INDEX `FKe1ju35ng8530tiklwi9p9iqb`(`variant_id` ASC) USING BTREE,
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKe1ju35ng8530tiklwi9p9iqb` FOREIGN KEY (`variant_id`) REFERENCES `motorcycle_variants` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKjddr6axxh7wxn50sblx69nx4e` FOREIGN KEY (`motorcycle_id`) REFERENCES `motorcycles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_items
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `order_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `payment_method` enum('COD','ONLINE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `payment_status` enum('FAILED','PAID','PENDING','REFUNDED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `shipping_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `shipping_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `shipping_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` enum('CANCELLED','CONFIRMED','DELIVERED','PENDING','PROCESSING','SHIPPED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `total_amount` decimal(12, 2) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `staff_id` bigint NULL DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKdhk2umg8ijjkg4njg6891trit`(`order_code` ASC) USING BTREE,
  INDEX `idx_orders_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_orders_status`(`status` ASC) USING BTREE,
  INDEX `idx_orders_created_at`(`created_at` ASC) USING BTREE,
  INDEX `FKe979ux6efhhi6ph712agy4bit`(`staff_id` ASC) USING BTREE,
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKe979ux6efhhi6ph712agy4bit` FOREIGN KEY (`staff_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for refresh_tokens
-- ----------------------------
DROP TABLE IF EXISTS `refresh_tokens`;
CREATE TABLE `refresh_tokens`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `device_info` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `expires_at` datetime(6) NOT NULL,
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_used_at` datetime(6) NULL DEFAULT NULL,
  `revoked` bit(1) NOT NULL,
  `token_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_refresh_tokens_token_hash`(`token_hash` ASC) USING BTREE,
  INDEX `idx_refresh_tokens_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of refresh_tokens
-- ----------------------------
INSERT INTO `refresh_tokens` VALUES (1, '2026-05-03 19:11:22.049419', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36', '2026-05-10 19:11:22.039003', '0:0:0:0:0:0:0:1', NULL, b'0', '62de5bf7a4a58a5e57c3c365df1d1ebbac1f073c7900d4572bccfbbd69b90915', 1);

-- ----------------------------
-- Table structure for site_config
-- ----------------------------
DROP TABLE IF EXISTS `site_config`;
CREATE TABLE `site_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `banner` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cta_primary_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cta_primary_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cta_secondary_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cta_secondary_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `favicon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `hero_subtitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `hero_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `primary_color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `secondary_color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `shop_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `slogan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of site_config
-- ----------------------------
INSERT INTO `site_config` VALUES (1, 'https://res.cloudinary.com/dasldvmai/image/upload/v1777810317/motorcycle_4f05e3002c614054aa7d7d1095ed0218.png', '/motorcycles', 'Khám phá xe máy', '/about', 'Tìm hiểu thêm', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777810307/motorcycle_4674963959d2479b95851c79e0d1fd30.png', 'Honda Việt Nam lần đầu tiên ra mắt mẫu xe máy điện mới Honda UC3 tại Việt Nam, công bố chính sách bán hàng mới dành cho mẫu xe máy điện CUV e: và bắt đầu triển khai hệ thống trạm sạc pin và trạm đổi pin', 'SẢN PHẨM VÀ DỊCH VỤ LÝ TƯỞNG', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777810304/motorcycle_71c5de24c1c448d3a1429575e3264d58.png', '#e31837', '#ffffff', 'Manh Tien Head', 'Honda viết tiếp ước mơ với tầm nhìn mang lại');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` enum('ADMIN','CUSTOMER','STAFF') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` enum('ACTIVE','BLOCKED','INACTIVE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK6dotkott2kjsp8vw4d0m25fb7`(`email` ASC) USING BTREE,
  INDEX `idx_users_email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '2026-05-03 18:12:07.244278', 'admin@honda.com', 'System Admin', '$2a$10$8zTwFTiy8FWOwGEyEHlWruy03nIuC0783fohch3cEg4ODQhMiqIMO', '0123456789', 'ADMIN', 'ACTIVE', '2026-05-03 18:12:07.244278');
INSERT INTO `users` VALUES (2, '2026-05-03 18:12:07.344403', 'staff@honda.com', 'Staff Member', '$2a$10$JcPLBfnHhH0ivV2DjSqnaesf9NP3yyWHve06blki.q1avdZavHeCy', '0123456788', 'STAFF', 'ACTIVE', '2026-05-03 18:12:07.344403');

-- ----------------------------
-- Table structure for variant_images
-- ----------------------------
DROP TABLE IF EXISTS `variant_images`;
CREATE TABLE `variant_images`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_thumbnail` bit(1) NOT NULL,
  `public_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort_order` int NOT NULL,
  `variant_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_variant_images_variant_id`(`variant_id` ASC) USING BTREE,
  CONSTRAINT `FKgrb51yestbwkd1gwy6d59cg3l` FOREIGN KEY (`variant_id`) REFERENCES `motorcycle_variants` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of variant_images
-- ----------------------------
INSERT INTO `variant_images` VALUES (1, '2026-05-03 18:12:07.462877', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777633197/motorcycle_8f385cc39a034b16b828fed11b66149a.jpg', b'1', 'HV-VISION-RED-1', 1, 1);
INSERT INTO `variant_images` VALUES (2, '2026-05-03 18:12:07.467626', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777633197/motorcycle_8f385cc39a034b16b828fed11b66149a.jpg', b'0', 'HV-VISION-RED-2', 2, 1);
INSERT INTO `variant_images` VALUES (3, '2026-05-03 18:12:07.471133', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777633197/motorcycle_8f385cc39a034b16b828fed11b66149a.jpg', b'0', 'HV-VISION-RED-3', 3, 1);
INSERT INTO `variant_images` VALUES (4, '2026-05-03 18:12:07.480133', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777794833/motorcycle_171c910ccd1d4c189b41cbb5c7d2b4c0.jpg', b'1', 'HV-VISION-WHT-1', 1, 2);
INSERT INTO `variant_images` VALUES (5, '2026-05-03 18:12:07.484138', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777794851/motorcycle_46848b3398744be38858bb8dbbf875f7.png', b'0', 'HV-VISION-WHT-2', 2, 2);
INSERT INTO `variant_images` VALUES (6, '2026-05-03 18:12:07.489040', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777794851/motorcycle_46848b3398744be38858bb8dbbf875f7.png', b'0', 'HV-VISION-WHT-3', 3, 2);
INSERT INTO `variant_images` VALUES (7, '2026-05-03 18:12:07.501487', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777807929/images_ugi2ew.jpg', b'1', 'SH-MODE-VNG-1', 1, 3);
INSERT INTO `variant_images` VALUES (8, '2026-05-03 18:12:07.506098', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777807992/2_m3rf1m.png', b'0', 'SH-MODE-VNG-2', 2, 3);
INSERT INTO `variant_images` VALUES (9, '2026-05-03 18:12:07.509894', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777807992/2_m3rf1m.png', b'0', 'SH-MODE-VNG-3', 3, 3);
INSERT INTO `variant_images` VALUES (10, '2026-05-03 18:12:07.518762', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808032/images_liclph.jpg', b'1', 'SH-MODE-RED-1', 1, 4);
INSERT INTO `variant_images` VALUES (11, '2026-05-03 18:12:07.522964', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808032/images_liclph.jpg', b'0', 'SH-MODE-RED-2', 2, 4);
INSERT INTO `variant_images` VALUES (12, '2026-05-03 18:12:07.527310', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808032/images_liclph.jpg', b'0', 'SH-MODE-RED-3', 3, 4);
INSERT INTO `variant_images` VALUES (13, '2026-05-03 18:12:07.541055', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808069/125-tieu-chuan-01_xhzpxn.png', b'1', 'AIR-BLADE-XANH-1', 1, 5);
INSERT INTO `variant_images` VALUES (14, '2026-05-03 18:12:07.544346', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808069/125-tieu-chuan-01_xhzpxn.png', b'0', 'AIR-BLADE-XANH-2', 2, 5);
INSERT INTO `variant_images` VALUES (15, '2026-05-03 18:12:07.548275', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808069/125-tieu-chuan-01_xhzpxn.png', b'0', 'AIR-BLADE-XANH-3', 3, 5);
INSERT INTO `variant_images` VALUES (16, '2026-05-03 18:12:07.558026', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808079/4_air_blade_125_2023_do_den_duoc_nang_cap_dong_co_esp_4_van_c089e41d1f_qe3ibh.jpg', b'1', 'AIR-BLADE-DO-1', 1, 6);
INSERT INTO `variant_images` VALUES (17, '2026-05-03 18:12:07.562092', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808079/4_air_blade_125_2023_do_den_duoc_nang_cap_dong_co_esp_4_van_c089e41d1f_qe3ibh.jpg', b'0', 'AIR-BLADE-DO-2', 2, 6);
INSERT INTO `variant_images` VALUES (18, '2026-05-03 18:12:07.565982', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808092/img_5605_7d0aa16fe1e747c9975dd9ae8ba224b6_vkoosw.png', b'0', 'AIR-BLADE-DO-3', 3, 6);
INSERT INTO `variant_images` VALUES (19, '2026-05-03 18:12:07.580292', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808117/images_fucs5z.jpg', b'1', 'PCX-DO-1', 1, 7);
INSERT INTO `variant_images` VALUES (20, '2026-05-03 18:12:07.584292', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808117/images_fucs5z.jpg', b'0', 'PCX-DO-2', 2, 7);
INSERT INTO `variant_images` VALUES (21, '2026-05-03 18:12:07.587296', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808117/images_fucs5z.jpg', b'0', 'PCX-DO-3', 3, 7);
INSERT INTO `variant_images` VALUES (22, '2026-05-03 18:12:07.596575', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808429/honda-pcx-160-2026-abs-xanh-duong_krp21b.jpg', b'1', 'PCX-XANH-1', 1, 8);
INSERT INTO `variant_images` VALUES (23, '2026-05-03 18:12:07.599951', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808429/honda-pcx-160-2026-abs-xanh-duong_krp21b.jpg', b'0', 'PCX-XANH-2', 2, 8);
INSERT INTO `variant_images` VALUES (24, '2026-05-03 18:12:07.603404', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808429/honda-pcx-160-2026-abs-xanh-duong_krp21b.jpg', b'0', 'PCX-XANH-3', 3, 8);
INSERT INTO `variant_images` VALUES (25, '2026-05-03 18:12:07.617610', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808247/images_pnwdrz.jpg', b'1', 'LEAD-TIM-1', 1, 9);
INSERT INTO `variant_images` VALUES (26, '2026-05-03 18:12:07.621964', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808245/images_xbctme.jpg', b'0', 'LEAD-TIM-2', 2, 9);
INSERT INTO `variant_images` VALUES (27, '2026-05-03 18:12:07.626154', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808245/images_xbctme.jpg', b'0', 'LEAD-TIM-3', 3, 9);
INSERT INTO `variant_images` VALUES (28, '2026-05-03 18:12:07.636055', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808250/images_of3h7a.jpg', b'1', 'LEAD-DO-1', 1, 10);
INSERT INTO `variant_images` VALUES (29, '2026-05-03 18:12:07.641308', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808250/images_of3h7a.jpg', b'0', 'LEAD-DO-2', 2, 10);
INSERT INTO `variant_images` VALUES (30, '2026-05-03 18:12:07.646308', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808250/images_of3h7a.jpg', b'0', 'LEAD-DO-3', 3, 10);
INSERT INTO `variant_images` VALUES (31, '2026-05-03 18:12:07.660381', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777796018/motorcycle_9ccc1bfc67e948cfa42b0af67f656044.jpg', b'1', 'WINNER-DO-1', 1, 11);
INSERT INTO `variant_images` VALUES (32, '2026-05-03 18:12:07.664941', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777796018/motorcycle_9ccc1bfc67e948cfa42b0af67f656044.jpg', b'0', 'WINNER-DO-2', 2, 11);
INSERT INTO `variant_images` VALUES (33, '2026-05-03 18:12:07.668096', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777796018/motorcycle_9ccc1bfc67e948cfa42b0af67f656044.jpg', b'0', 'WINNER-DO-3', 3, 11);
INSERT INTO `variant_images` VALUES (34, '2026-05-03 18:12:07.678247', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777796036/motorcycle_eeeb7a919a2941c28795f5e05c64eacc.jpg', b'1', 'WINNER-DEN-1', 1, 12);
INSERT INTO `variant_images` VALUES (35, '2026-05-03 18:12:07.682242', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777796036/motorcycle_eeeb7a919a2941c28795f5e05c64eacc.jpg', b'0', 'WINNER-DEN-2', 2, 12);
INSERT INTO `variant_images` VALUES (36, '2026-05-03 18:12:07.686243', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777796036/motorcycle_eeeb7a919a2941c28795f5e05c64eacc.jpg', b'0', 'WINNER-DEN-3', 3, 12);
INSERT INTO `variant_images` VALUES (37, '2026-05-03 18:12:07.700774', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'1', 'CBR-RED-1', 1, 13);
INSERT INTO `variant_images` VALUES (38, '2026-05-03 18:12:07.704773', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'0', 'CBR-RED-2', 2, 13);
INSERT INTO `variant_images` VALUES (39, '2026-05-03 18:12:07.709126', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'0', 'CBR-RED-3', 3, 13);
INSERT INTO `variant_images` VALUES (40, '2026-05-03 18:12:07.718079', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808947/images_flzcql.jpg', b'1', 'CBR-BLK-1', 1, 14);
INSERT INTO `variant_images` VALUES (41, '2026-05-03 18:12:07.723087', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808947/images_flzcql.jpg', b'0', 'CBR-BLK-2', 2, 14);
INSERT INTO `variant_images` VALUES (42, '2026-05-03 18:12:07.727227', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808947/images_flzcql.jpg', b'0', 'CBR-BLK-3', 3, 14);
INSERT INTO `variant_images` VALUES (43, '2026-05-03 18:12:07.741913', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808990/images_hjhol8.jpg', b'1', 'CB650R-RED-1', 1, 15);
INSERT INTO `variant_images` VALUES (44, '2026-05-03 18:12:07.746295', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808990/images_hjhol8.jpg', b'0', 'CB650R-RED-2', 2, 15);
INSERT INTO `variant_images` VALUES (45, '2026-05-03 18:12:07.751617', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808990/images_hjhol8.jpg', b'0', 'CB650R-RED-3', 3, 15);
INSERT INTO `variant_images` VALUES (46, '2026-05-03 18:12:07.761129', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'1', 'CB650R-BLK-1', 1, 16);
INSERT INTO `variant_images` VALUES (47, '2026-05-03 18:12:07.765422', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'CB650R-BLK-2', 2, 16);
INSERT INTO `variant_images` VALUES (48, '2026-05-03 18:12:07.770612', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'CB650R-BLK-3', 3, 16);
INSERT INTO `variant_images` VALUES (49, '2026-05-03 18:12:07.783730', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'1', 'CB500F-RED-1', 1, 17);
INSERT INTO `variant_images` VALUES (50, '2026-05-03 18:12:07.787968', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'0', 'CB500F-RED-2', 2, 17);
INSERT INTO `variant_images` VALUES (51, '2026-05-03 18:12:07.792467', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'0', 'CB500F-RED-3', 3, 17);
INSERT INTO `variant_images` VALUES (52, '2026-05-03 18:12:07.800288', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'1', 'CB500F-WHT-1', 1, 18);
INSERT INTO `variant_images` VALUES (53, '2026-05-03 18:12:07.805088', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'CB500F-WHT-2', 2, 18);
INSERT INTO `variant_images` VALUES (54, '2026-05-03 18:12:07.810842', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'CB500F-WHT-3', 3, 18);
INSERT INTO `variant_images` VALUES (55, '2026-05-03 18:12:07.822535', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'1', 'CRF-WRN-1', 1, 19);
INSERT INTO `variant_images` VALUES (56, '2026-05-03 18:12:07.825863', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'CRF-WRN-2', 2, 19);
INSERT INTO `variant_images` VALUES (57, '2026-05-03 18:12:07.829172', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'CRF-WRN-3', 3, 19);
INSERT INTO `variant_images` VALUES (58, '2026-05-03 18:12:07.837905', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'1', 'CRF-BLK-1', 1, 20);
INSERT INTO `variant_images` VALUES (59, '2026-05-03 18:12:07.841977', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'CRF-BLK-2', 2, 20);
INSERT INTO `variant_images` VALUES (60, '2026-05-03 18:12:07.846555', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'CRF-BLK-3', 3, 20);
INSERT INTO `variant_images` VALUES (61, '2026-05-03 18:12:07.857275', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809195/Grande-Limit-Pink-Orange-Metallic-004-768x645_peoy69.png', b'1', 'YG-GRANDE-CRM-1', 1, 21);
INSERT INTO `variant_images` VALUES (62, '2026-05-03 18:12:07.860929', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809195/Grande-Limit-Pink-Orange-Metallic-004-768x645_peoy69.png', b'0', 'YG-GRANDE-CRM-2', 2, 21);
INSERT INTO `variant_images` VALUES (63, '2026-05-03 18:12:07.864927', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809195/Grande-Limit-Pink-Orange-Metallic-004-768x645_peoy69.png', b'0', 'YG-GRANDE-CRM-3', 3, 21);
INSERT INTO `variant_images` VALUES (64, '2026-05-03 18:12:07.872465', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'1', 'YG-GRANDE-GRY-1', 1, 22);
INSERT INTO `variant_images` VALUES (65, '2026-05-03 18:12:07.876816', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'0', 'YG-GRANDE-GRY-2', 2, 22);
INSERT INTO `variant_images` VALUES (66, '2026-05-03 18:12:07.879816', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'0', 'YG-GRANDE-GRY-3', 3, 22);
INSERT INTO `variant_images` VALUES (67, '2026-05-03 18:12:07.892625', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'1', 'JANUS-TIM-1', 1, 23);
INSERT INTO `variant_images` VALUES (68, '2026-05-03 18:12:07.896621', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'0', 'JANUS-TIM-2', 2, 23);
INSERT INTO `variant_images` VALUES (69, '2026-05-03 18:12:07.899621', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'0', 'JANUS-TIM-3', 3, 23);
INSERT INTO `variant_images` VALUES (70, '2026-05-03 18:12:07.907625', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'1', 'JANUS-DO-1', 1, 24);
INSERT INTO `variant_images` VALUES (71, '2026-05-03 18:12:07.911628', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'0', 'JANUS-DO-2', 2, 24);
INSERT INTO `variant_images` VALUES (72, '2026-05-03 18:12:07.914624', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'0', 'JANUS-DO-3', 3, 24);
INSERT INTO `variant_images` VALUES (73, '2026-05-03 18:12:07.928151', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809289/2cb6f7c9-cfd3-4367-9727-968b32749124_2024_Zero_FX_Slate_shadow_wdgogw.png', b'1', 'FX-GLD-1', 1, 25);
INSERT INTO `variant_images` VALUES (74, '2026-05-03 18:12:07.932151', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809289/2cb6f7c9-cfd3-4367-9727-968b32749124_2024_Zero_FX_Slate_shadow_wdgogw.png', b'0', 'FX-GLD-2', 2, 25);
INSERT INTO `variant_images` VALUES (75, '2026-05-03 18:12:07.936150', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809289/2cb6f7c9-cfd3-4367-9727-968b32749124_2024_Zero_FX_Slate_shadow_wdgogw.png', b'0', 'FX-GLD-3', 3, 25);
INSERT INTO `variant_images` VALUES (76, '2026-05-03 18:12:07.945151', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809300/images_labgn5.jpg', b'1', 'FX-SLV-1', 1, 26);
INSERT INTO `variant_images` VALUES (77, '2026-05-03 18:12:07.949150', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809300/images_labgn5.jpg', b'0', 'FX-SLV-2', 2, 26);
INSERT INTO `variant_images` VALUES (78, '2026-05-03 18:12:07.953491', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809300/images_labgn5.jpg', b'0', 'FX-SLV-3', 3, 26);
INSERT INTO `variant_images` VALUES (79, '2026-05-03 18:12:07.965554', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809327/images_uv3gw7.jpg', b'1', 'EXCITER-DO-1', 1, 27);
INSERT INTO `variant_images` VALUES (80, '2026-05-03 18:12:07.968553', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809327/images_uv3gw7.jpg', b'0', 'EXCITER-DO-2', 2, 27);
INSERT INTO `variant_images` VALUES (81, '2026-05-03 18:12:07.972066', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809327/images_uv3gw7.jpg', b'0', 'EXCITER-DO-3', 3, 27);
INSERT INTO `variant_images` VALUES (82, '2026-05-03 18:12:07.979117', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809517/Exciter-Matte-Black-002.jpec-copy-20160628-14061615_hahr1q.jpg', b'1', 'EXCITER-DEN-1', 1, 28);
INSERT INTO `variant_images` VALUES (83, '2026-05-03 18:12:07.982622', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809517/Exciter-Matte-Black-002.jpec-copy-20160628-14061615_hahr1q.jpg', b'0', 'EXCITER-DEN-2', 2, 28);
INSERT INTO `variant_images` VALUES (84, '2026-05-03 18:12:07.986592', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809517/Exciter-Matte-Black-002.jpec-copy-20160628-14061615_hahr1q.jpg', b'0', 'EXCITER-DEN-3', 3, 28);
INSERT INTO `variant_images` VALUES (85, '2026-05-03 18:12:08.001648', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'1', 'NVX-DO-1', 1, 29);
INSERT INTO `variant_images` VALUES (86, '2026-05-03 18:12:08.006262', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'0', 'NVX-DO-2', 2, 29);
INSERT INTO `variant_images` VALUES (87, '2026-05-03 18:12:08.011619', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'0', 'NVX-DO-3', 3, 29);
INSERT INTO `variant_images` VALUES (88, '2026-05-03 18:12:08.019932', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809547/images_lgy475.jpg', b'1', 'NVX-XANH-1', 1, 30);
INSERT INTO `variant_images` VALUES (89, '2026-05-03 18:12:08.025533', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809547/images_lgy475.jpg', b'0', 'NVX-XANH-2', 2, 30);
INSERT INTO `variant_images` VALUES (90, '2026-05-03 18:12:08.030133', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809547/images_lgy475.jpg', b'0', 'NVX-XANH-3', 3, 30);
INSERT INTO `variant_images` VALUES (91, '2026-05-03 18:12:08.043116', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'1', 'R15-V4-DO-1', 1, 31);
INSERT INTO `variant_images` VALUES (92, '2026-05-03 18:12:08.047242', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'0', 'R15-V4-DO-2', 2, 31);
INSERT INTO `variant_images` VALUES (93, '2026-05-03 18:12:08.052470', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808923/images_rdaa2n.jpg', b'0', 'R15-V4-DO-3', 3, 31);
INSERT INTO `variant_images` VALUES (94, '2026-05-03 18:12:08.062298', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808947/images_flzcql.jpg', b'1', 'R15-V4-BLU-1', 1, 32);
INSERT INTO `variant_images` VALUES (95, '2026-05-03 18:12:08.066780', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808947/images_flzcql.jpg', b'0', 'R15-V4-BLU-2', 2, 32);
INSERT INTO `variant_images` VALUES (96, '2026-05-03 18:12:08.072227', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808947/images_flzcql.jpg', b'0', 'R15-V4-BLU-3', 3, 32);
INSERT INTO `variant_images` VALUES (97, '2026-05-03 18:12:08.087218', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'1', 'MT15-DEN-1', 1, 33);
INSERT INTO `variant_images` VALUES (98, '2026-05-03 18:12:08.092730', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'MT15-DEN-2', 2, 33);
INSERT INTO `variant_images` VALUES (99, '2026-05-03 18:12:08.096107', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'MT15-DEN-3', 3, 33);
INSERT INTO `variant_images` VALUES (100, '2026-05-03 18:12:08.111435', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'1', 'MT15-CAM-1', 1, 34);
INSERT INTO `variant_images` VALUES (101, '2026-05-03 18:12:08.118435', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'MT15-CAM-2', 2, 34);
INSERT INTO `variant_images` VALUES (102, '2026-05-03 18:12:08.125044', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'MT15-CAM-3', 3, 34);
INSERT INTO `variant_images` VALUES (103, '2026-05-03 18:12:08.135854', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'1', 'MT07-DEN-1', 1, 35);
INSERT INTO `variant_images` VALUES (104, '2026-05-03 18:12:08.139586', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'MT07-DEN-2', 2, 35);
INSERT INTO `variant_images` VALUES (105, '2026-05-03 18:12:08.144145', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'MT07-DEN-3', 3, 35);
INSERT INTO `variant_images` VALUES (106, '2026-05-03 18:12:08.150840', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'1', 'MT07-CAM-1', 1, 36);
INSERT INTO `variant_images` VALUES (107, '2026-05-03 18:12:08.155631', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'MT07-CAM-2', 2, 36);
INSERT INTO `variant_images` VALUES (108, '2026-05-03 18:12:08.187481', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'MT07-CAM-3', 3, 36);
INSERT INTO `variant_images` VALUES (109, '2026-05-03 18:12:08.197449', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809575/red-white-1374223766_hqkuxn.jpg', b'1', 'RAIDER-DO-1', 1, 37);
INSERT INTO `variant_images` VALUES (110, '2026-05-03 18:12:08.200707', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809575/red-white-1374223766_hqkuxn.jpg', b'0', 'RAIDER-DO-2', 2, 37);
INSERT INTO `variant_images` VALUES (111, '2026-05-03 18:12:08.203952', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809575/red-white-1374223766_hqkuxn.jpg', b'0', 'RAIDER-DO-3', 3, 37);
INSERT INTO `variant_images` VALUES (112, '2026-05-03 18:12:08.210445', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809680/images_nvlhrx.jpg', b'1', 'RAIDER-DEN-1', 1, 38);
INSERT INTO `variant_images` VALUES (113, '2026-05-03 18:12:08.214077', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809680/images_nvlhrx.jpg', b'0', 'RAIDER-DEN-2', 2, 38);
INSERT INTO `variant_images` VALUES (114, '2026-05-03 18:12:08.218241', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809680/images_nvlhrx.jpg', b'0', 'RAIDER-DEN-3', 3, 38);
INSERT INTO `variant_images` VALUES (115, '2026-05-03 18:12:08.228700', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'1', 'GSX-DO-1', 1, 39);
INSERT INTO `variant_images` VALUES (116, '2026-05-03 18:12:08.233712', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'0', 'GSX-DO-2', 2, 39);
INSERT INTO `variant_images` VALUES (117, '2026-05-03 18:12:08.238231', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'0', 'GSX-DO-3', 3, 39);
INSERT INTO `variant_images` VALUES (118, '2026-05-03 18:12:08.244711', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'1', 'GSX-DEN-1', 1, 40);
INSERT INTO `variant_images` VALUES (119, '2026-05-03 18:12:08.250249', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'0', 'GSX-DEN-2', 2, 40);
INSERT INTO `variant_images` VALUES (120, '2026-05-03 18:12:08.254175', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809535/images_ofyvx4.jpg', b'0', 'GSX-DEN-3', 3, 40);
INSERT INTO `variant_images` VALUES (121, '2026-05-03 18:12:08.264539', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809721/Suzuki-Address-110-SE-ban-dac-biet-moi-ra-mat-gia-binh-dan-su2-1628385689-63-width660height462_rbykpw.jpg', b'1', 'ADDRESS-TIM-1', 1, 41);
INSERT INTO `variant_images` VALUES (122, '2026-05-03 18:12:08.268004', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809721/Suzuki-Address-110-SE-ban-dac-biet-moi-ra-mat-gia-binh-dan-su2-1628385689-63-width660height462_rbykpw.jpg', b'0', 'ADDRESS-TIM-2', 2, 41);
INSERT INTO `variant_images` VALUES (123, '2026-05-03 18:12:08.272078', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809721/Suzuki-Address-110-SE-ban-dac-biet-moi-ra-mat-gia-binh-dan-su2-1628385689-63-width660height462_rbykpw.jpg', b'0', 'ADDRESS-TIM-3', 3, 41);
INSERT INTO `variant_images` VALUES (124, '2026-05-03 18:12:08.280217', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809547/images_lgy475.jpg', b'1', 'ADDRESS-XANH-1', 1, 42);
INSERT INTO `variant_images` VALUES (125, '2026-05-03 18:12:08.283654', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809547/images_lgy475.jpg', b'0', 'ADDRESS-XANH-2', 2, 42);
INSERT INTO `variant_images` VALUES (126, '2026-05-03 18:12:08.288578', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809547/images_lgy475.jpg', b'0', 'ADDRESS-XANH-3', 3, 42);
INSERT INTO `variant_images` VALUES (127, '2026-05-03 18:12:08.299135', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809761/images_xu0wd6.jpg', b'1', 'BURGMAN-DEN-1', 1, 43);
INSERT INTO `variant_images` VALUES (128, '2026-05-03 18:12:08.303151', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809761/images_xu0wd6.jpg', b'0', 'BURGMAN-DEN-2', 2, 43);
INSERT INTO `variant_images` VALUES (129, '2026-05-03 18:12:08.307148', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809761/images_xu0wd6.jpg', b'0', 'BURGMAN-DEN-3', 3, 43);
INSERT INTO `variant_images` VALUES (130, '2026-05-03 18:12:08.314851', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808429/honda-pcx-160-2026-abs-xanh-duong_krp21b.jpg', b'1', 'BURGMAN-BAC-1', 1, 44);
INSERT INTO `variant_images` VALUES (131, '2026-05-03 18:12:08.317848', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808429/honda-pcx-160-2026-abs-xanh-duong_krp21b.jpg', b'0', 'BURGMAN-BAC-2', 2, 44);
INSERT INTO `variant_images` VALUES (132, '2026-05-03 18:12:08.321028', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808429/honda-pcx-160-2026-abs-xanh-duong_krp21b.jpg', b'0', 'BURGMAN-BAC-3', 3, 44);
INSERT INTO `variant_images` VALUES (133, '2026-05-03 18:12:08.332606', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'1', 'N400-LIMO-1', 1, 45);
INSERT INTO `variant_images` VALUES (134, '2026-05-03 18:12:08.336528', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'N400-LIMO-2', 2, 45);
INSERT INTO `variant_images` VALUES (135, '2026-05-03 18:12:08.340276', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'N400-LIMO-3', 3, 45);
INSERT INTO `variant_images` VALUES (136, '2026-05-03 18:12:08.346890', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'1', 'N400-DEN-1', 1, 46);
INSERT INTO `variant_images` VALUES (137, '2026-05-03 18:12:08.349986', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'N400-DEN-2', 2, 46);
INSERT INTO `variant_images` VALUES (138, '2026-05-03 18:12:08.354766', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'N400-DEN-3', 3, 46);
INSERT INTO `variant_images` VALUES (139, '2026-05-03 18:12:08.367260', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'1', 'Z400-LIMO-1', 1, 47);
INSERT INTO `variant_images` VALUES (140, '2026-05-03 18:12:08.370261', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'Z400-LIMO-2', 2, 47);
INSERT INTO `variant_images` VALUES (141, '2026-05-03 18:12:08.373829', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809035/1714129968-2022-honda-cb500f-review-price-spec_003_ssmwos.jpg', b'0', 'Z400-LIMO-3', 3, 47);
INSERT INTO `variant_images` VALUES (142, '2026-05-03 18:12:08.382273', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'1', 'Z400-DEN-1', 1, 48);
INSERT INTO `variant_images` VALUES (143, '2026-05-03 18:12:08.385774', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'Z400-DEN-2', 2, 48);
INSERT INTO `variant_images` VALUES (144, '2026-05-03 18:12:08.389187', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777808999/hoang_viet_motors_-_cb500_hornet_-_den_b0646f38ed7e49bf9fbbbfabc9050afe_c7jnqy.png', b'0', 'Z400-DEN-3', 3, 48);
INSERT INTO `variant_images` VALUES (145, '2026-05-03 18:12:08.400842', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777793513/motorcycle_ff8cc9b3b54546d2b92ee279bb2af0c4.png', b'1', 'VERSYS-WRN-1', 1, 49);
INSERT INTO `variant_images` VALUES (146, '2026-05-03 18:12:08.405141', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777793513/motorcycle_ff8cc9b3b54546d2b92ee279bb2af0c4.png', b'0', 'VERSYS-WRN-2', 2, 49);
INSERT INTO `variant_images` VALUES (147, '2026-05-03 18:12:08.408474', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777793513/motorcycle_ff8cc9b3b54546d2b92ee279bb2af0c4.png', b'0', 'VERSYS-WRN-3', 3, 49);
INSERT INTO `variant_images` VALUES (148, '2026-05-03 18:12:08.418778', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777793637/motorcycle_d1a1857fed8d430e8408a1a1a72e73e8.png', b'1', 'VERSYS-DEN-1', 1, 50);
INSERT INTO `variant_images` VALUES (149, '2026-05-03 18:12:08.422973', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777793637/motorcycle_d1a1857fed8d430e8408a1a1a72e73e8.png', b'0', 'VERSYS-DEN-2', 2, 50);
INSERT INTO `variant_images` VALUES (150, '2026-05-03 18:12:08.426048', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777793637/motorcycle_d1a1857fed8d430e8408a1a1a72e73e8.png', b'0', 'VERSYS-DEN-3', 3, 50);
INSERT INTO `variant_images` VALUES (151, '2026-05-03 18:12:08.437714', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'1', 'SPRINT-DO-1', 1, 51);
INSERT INTO `variant_images` VALUES (152, '2026-05-03 18:12:08.440977', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'0', 'SPRINT-DO-2', 2, 51);
INSERT INTO `variant_images` VALUES (153, '2026-05-03 18:12:08.444244', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'0', 'SPRINT-DO-3', 3, 51);
INSERT INTO `variant_images` VALUES (154, '2026-05-03 18:12:08.452493', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'1', 'SPRINT-TRANG-1', 1, 52);
INSERT INTO `variant_images` VALUES (155, '2026-05-03 18:12:08.455937', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'0', 'SPRINT-TRANG-2', 2, 52);
INSERT INTO `variant_images` VALUES (156, '2026-05-03 18:12:08.459294', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'0', 'SPRINT-TRANG-3', 3, 52);
INSERT INTO `variant_images` VALUES (157, '2026-05-03 18:12:08.471190', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'1', 'PRIMAVERA-TIM-1', 1, 53);
INSERT INTO `variant_images` VALUES (158, '2026-05-03 18:12:08.474188', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'0', 'PRIMAVERA-TIM-2', 2, 53);
INSERT INTO `variant_images` VALUES (159, '2026-05-03 18:12:08.478191', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'0', 'PRIMAVERA-TIM-3', 3, 53);
INSERT INTO `variant_images` VALUES (160, '2026-05-03 18:12:08.485738', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'1', 'PRIMAVERA-TRANG-1', 1, 54);
INSERT INTO `variant_images` VALUES (161, '2026-05-03 18:12:08.488734', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'0', 'PRIMAVERA-TRANG-2', 2, 54);
INSERT INTO `variant_images` VALUES (162, '2026-05-03 18:12:08.491734', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809226/yamaha-grande-2026-gioi-han-bac-den_k3uaia.jpg', b'0', 'PRIMAVERA-TRANG-3', 3, 54);
INSERT INTO `variant_images` VALUES (163, '2026-05-03 18:12:08.509765', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'1', 'LIBERTY-TIM-1', 1, 55);
INSERT INTO `variant_images` VALUES (164, '2026-05-03 18:12:08.514118', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'0', 'LIBERTY-TIM-2', 2, 55);
INSERT INTO `variant_images` VALUES (165, '2026-05-03 18:12:08.517278', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809243/Ja-Pre-2024-Mat-Blue-004-768x576_fbfkl9.png', b'0', 'LIBERTY-TIM-3', 3, 55);
INSERT INTO `variant_images` VALUES (166, '2026-05-03 18:12:08.524859', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'1', 'LIBERTY-DO-1', 1, 56);
INSERT INTO `variant_images` VALUES (167, '2026-05-03 18:12:08.528174', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'0', 'LIBERTY-DO-2', 2, 56);
INSERT INTO `variant_images` VALUES (168, '2026-05-03 18:12:08.532144', 'https://res.cloudinary.com/dasldvmai/image/upload/v1777809236/Ja-Std-2024-Red-Metallic-004-768x645_u8g6qr.png', b'0', 'LIBERTY-DO-3', 3, 56);

SET FOREIGN_KEY_CHECKS = 1;
