# Honda Dealership API - Postman Collection

Complete API testing collection for Honda Dealership E-commerce Backend.

## 📁 Files Included

| File | Description |
|------|-------------|
| `Honda-Dealership.postman_collection.json` | Full API collection with 50+ endpoints |
| `Honda-Dealership.postman_environment.json` | Environment variables configuration |

## 🚀 How to Import

### Step 1: Import Collection

1. Open Postman
2. Click **Import** button (top left)
3. Drag & drop `Honda-Dealership.postman_collection.json` OR click "Upload Files"
4. Select the JSON file
5. Click **Import**

### Step 2: Import Environment

1. In Postman, click the **Environments** dropdown (top right, near "No Environment")
2. Click **Import**
3. Drag & drop `Honda-Dealership.postman_environment.json`
4. Click **Import**

### Step 3: Select Environment

1. Click the **Environments** dropdown
2. Select "Honda Dealership - Development"

## ⚙️ Configuration

### Default Values (From Seeder)

| Variable | Default | Description |
|----------|---------|-------------|
| `baseUrl` | `http://localhost:8080/honda/api/v1` | API base URL (with context-path) |
| `adminEmail` | `admin@honda.com` | Admin user email |
| `adminPassword` | `Admin@123` | Admin user password |
| `staffEmail` | `staff@honda.com` | Staff user email |
| `staffPassword` | `Staff@123` | Staff user password |
| `customerEmail` | `customer@example.com` | Customer email |
| `customerPassword` | `Customer@123` | Customer password |

> **Note**: Update passwords in environment to match your seeded data or `.env` configuration.

## 📊 Seeded Data Reference

### Users (from SeedConfig)
| Role | Email | Password |
|------|-------|----------|
| Admin | `admin@honda.com` | `Admin@123` |
| Staff | `staff@honda.com` | `Staff@123` |

### Brands (ID)
| ID | Name |
|----|------|
| 1 | Honda |
| 2 | Yamaha |
| 3 | Suzuki |
| 4 | Kawasaki |
| 5 | Piaggio |

### Categories (ID)
| ID | Name |
|----|------|
| 1 | Scooter |
| 2 | Sport |
| 3 | Naked |
| 4 | Cruiser |
| 5 | Adventure |
| 6 | Electric |

### Motorcycles (ID)
| ID | Name | Brand | Category |
|----|------|-------|----------|
| 1 | Honda Vision 2026 | Honda (1) | Scooter (1) |
| 2 | Honda Winner X | Honda (1) | Sport (2) |
| 3 | Yamaha Grande 2026 | Yamaha (2) | Scooter (1) |

### Variants (ID)
| ID | Motorcycle | Variant Name | Color | Price |
|----|------------|--------------|-------|-------|
| 1 | Vision (1) | Standard Red | Red | 36,000,000 |
| 2 | Vision (1) | Standard White | White | 36,000,000 |
| 3 | Vision (1) | Standard Black | Black | 36,500,000 |
| 4 | Winner X (2) | Sport Red | Red Racing | 48,000,000 |
| 5 | Winner X (2) | Matt Black | Black Matt | 48,000,000 |
| 6 | Grande (3) | Premium Cream | Cream | 42,000,000 |
| 7 | Grande (3) | Premium Gray | Gray | 42,000,000 |
| 8 | Grande (3) | Premium Black | Black | 43,000,000 |

## 🎯 Recommended Test Order

### Phase 1: Authentication (Public)
```
1. POST Register (Customer) - Create test account
2. POST Login (Admin) - Get admin token
3. POST Login (Staff) - Get staff token
4. POST Login (Customer) - Get customer token
5. GET My Profile - Verify authentication
```

### Phase 2: Public APIs (No Auth Required)
```
6. GET All Motorcycles
7. GET Motorcycle by ID
8. GET Motorcycle by Slug
9. GET Search Motorcycles
10. GET All Brands
11. GET All Categories
12. GET All Variants
13. GET Variants by Motorcycle ID
14. GET Images by Variant
```

### Phase 3: Cart & Orders (Customer Required)
```
15. POST Login (Customer) - Get customer token
16. GET My Cart
17. POST Add To Cart
18. PUT Update Cart Item Quantity
19. POST Checkout / Create Order
20. GET My Orders
21. GET Order Detail
22. PUT Cancel Order
```

### Phase 4: Admin - Products (Admin Required)
```
23. POST Login (Admin)
24. POST Create Brand
25. POST Create Category
26. POST Create Motorcycle
27. PUT Update Motorcycle
28. POST Create Variant
29. PUT Update Variant
30. DELETE Variant
31. DELETE Motorcycle
```

### Phase 5: Admin - Dashboard (Admin Required)
```
32. GET Dashboard Summary
33. GET Revenue
34. GET Top Products
35. GET Order Status
```

### Phase 6: Staff - Orders (Staff/Admin Required)
```
36. POST Login (Staff)
37. GET All Orders (Staff)
38. GET Order Detail (Staff)
39. PUT Confirm Order
40. PUT Preparing Order
41. PUT Shipping Order
42. PUT Delivered Order
43. PUT Cancel Order
```

### Phase 7: Image Upload (Admin/Staff Required)
```
44. POST Upload Variant Image
45. PUT Set Thumbnail
46. DELETE Image
```

## 📋 API Endpoints Summary

> **Important**: The application uses context-path `/honda`, so all URLs start with `http://localhost:8080/honda/api/v1`

### Public APIs (`/api/v1`)

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| **Auth** | | | |
| POST | `/auth/register` | ❌ | Register new customer |
| POST | `/auth/login` | ❌ | Login |
| POST | `/auth/refresh` | ❌ | Refresh token |
| POST | `/auth/logout` | ✅ | Logout |
| GET | `/auth/me` | ✅ | Get profile |
| **Products** | | | |
| GET | `/motorcycles` | ❌ | List motorcycles (paginated) |
| GET | `/motorcycles/{id}` | ❌ | Get motorcycle detail |
| GET | `/motorcycles/slug/{slug}` | ❌ | Get by slug |
| GET | `/motorcycles/search` | ❌ | Search motorcycles |
| GET | `/motorcycles/{id}/variants` | ❌ | Get variants by motorcycle |
| **Brands** | | | |
| GET | `/brands` | ❌ | List all brands |
| **Categories** | | | |
| GET | `/categories` | ❌ | List all categories |
| **Variants** | | | |
| GET | `/variants` | ❌ | List all variants (paginated) |
| GET | `/variants/{id}` | ❌ | Get variant detail |
| GET | `/variants/{id}/images` | ❌ | Get variant images |
| **Cart** | | | |
| GET | `/cart` | ✅ | Get my cart |
| POST | `/cart/items` | ✅ | Add to cart |
| PUT | `/cart/items/{id}` | ✅ | Update quantity |
| DELETE | `/cart/items/{id}` | ✅ | Remove item |
| DELETE | `/cart/clear` | ✅ | Clear cart |
| **Orders** | | | |
| POST | `/orders/checkout` | ✅ | Create order |
| GET | `/orders/my-orders` | ✅ | My orders |
| GET | `/orders/{id}` | ✅ | Order detail |
| PUT | `/orders/{id}/cancel` | ✅ | Cancel order |

### Admin APIs (`/api/v1/admin`)

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/motorcycles` | ADMIN | Create motorcycle |
| PUT | `/motorcycles/{id}` | ADMIN | Update motorcycle |
| DELETE | `/motorcycles/{id}` | ADMIN | Delete motorcycle |
| POST | `/brands` | ADMIN | Create brand |
| POST | `/categories` | ADMIN | Create category |
| POST | `/variants` | ADMIN | Create variant |
| PUT | `/variants/{id}` | ADMIN | Update variant |
| DELETE | `/variants/{id}` | ADMIN | Delete variant |
| GET | `/orders` | ADMIN/STAFF | All orders |
| GET | `/orders/{id}` | ADMIN/STAFF | Order detail |
| PUT | `/orders/{id}/status` | ADMIN/STAFF | Update status |
| GET | `/dashboard/summary` | ADMIN | Dashboard summary |
| GET | `/dashboard/revenue` | ADMIN | Revenue data |
| GET | `/dashboard/top-products` | ADMIN | Top products |
| GET | `/dashboard/order-status` | ADMIN | Order status stats |

### Staff APIs (`/api/v1/staff`)

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/orders` | STAFF/ADMIN | List orders |
| GET | `/orders/{id}` | STAFF/ADMIN | Order detail |
| PUT | `/orders/{id}/confirm` | STAFF/ADMIN | Confirm order |
| PUT | `/orders/{id}/preparing` | STAFF/ADMIN | Mark preparing |
| PUT | `/orders/{id}/shipping` | STAFF/ADMIN | Mark shipping |
| PUT | `/orders/{id}/delivered` | STAFF/ADMIN | Mark delivered |
| PUT | `/orders/{id}/cancel` | STAFF/ADMIN | Cancel order |

### Image APIs (`/api/admin`)

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/variants/{id}/images` | ADMIN/STAFF | Upload image |
| PUT | `/images/{id}/thumbnail` | ADMIN/STAFF | Set thumbnail |
| DELETE | `/images/{id}` | ADMIN/STAFF | Delete image |

## 🔐 Authentication

This collection uses **Bearer Token** authentication.

- Tokens are automatically saved after login via Postman scripts
- Authorization header is automatically added to secured endpoints
- Public endpoints (auth, products, brands, categories, variants) don't require auth

## 🧪 Test Scripts

Each request includes basic validation tests:
- Status code check
- Response has `code` field
- Response has `result` field

## 📝 Notes

1. **Base URL**: The collection uses `{{baseUrl}}` variable, default is `http://localhost:8080/api/v1`
2. **Image Upload**: Use `multipart/form-data` for file uploads
3. **IDs**: Some requests will fail if IDs don't exist - create resources first or update environment variables
4. **Token Expiry**: If token expires, re-login and tokens will be updated automatically

## 🆘 Troubleshooting

| Issue | Solution |
|-------|----------|
| 401 Unauthorized | Re-run login request to get new token |
| 404 Not Found | Check if resource ID exists or update variable |
| 403 Forbidden | Ensure correct role for endpoint (Admin/Staff/Customer) |
| Connection refused | Ensure backend is running on port 8080 |

## 📞 Support

For issues or questions, check:
1. Backend server is running
2. Database is accessible
3. JWT secret is configured correctly
4. Cloudinary credentials are set (for image upload)