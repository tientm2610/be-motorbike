# Honda Dealership API - Postman Collection

## Overview

This Postman collection contains all API endpoints for the Honda Dealership Motorbike E-commerce Platform.

### Standard Response Format
```json
{
  "code": 1000,
  "message": "Success",
  "result": {}
}
```

### Authentication (JWT)
- **Access Token**: 15 minutes validity
- **Refresh Token**: 7 days validity
- Login returns both tokens: `accessToken` + `refreshToken`
- Use `/auth/refresh` to get new tokens when access expires

### Roles
- **ADMIN**: Full system access
- **STAFF**: Order management
- **CUSTOMER**: Cart and order access

---

## Getting Started

### 1. Import Collection

1. Open Postman
2. Click **Import** button
3. Select the `Honda-Dealership.postman_collection.json` file
4. Collection will appear in your collections

### 2. Import Environment

1. Click the **Environments** dropdown (top right)
2. Click **Import**
3. Select the `Honda-Dealership.local.postman_environment.json` file
4. Select the "Honda Dealership - Local" environment

### 3. Verify Configuration

- `baseUrl` should be: `http://localhost:8080`
- Make sure the Spring Boot app is running on port 8080

### 4. Start Testing

1. **Login as Admin** → Tokens auto-saved to `adminToken` + `adminRefreshToken`
2. **Login as Staff** → Tokens auto-saved to `staffToken` + `staffRefreshToken`
3. **Login as Customer** → Tokens auto-saved to `customerToken` + `customerRefreshToken`

> Tokens are automatically saved after successful login. Use the correct token for each role.

### Step 2: Test Public APIs

1. Get all motorcycles
2. Get product detail
3. Get brands
4. Get categories

### Step 3: Test Admin APIs

1. Create motorcycle
2. Create variant
3. Create brand
4. Create category
5. Delete variant
6. Delete motorcycle

### Step 4: Test Customer Cart & Orders

1. Add to cart
2. Get cart
3. Update cart item
4. Checkout
5. Get my orders
6. Cancel order

### Step 5: Test Staff Order Management

1. Get all orders
2. Confirm order
3. Preparing order
4. Shipping order
5. Delivered order
6. Cancel order

### Step 6: Test Admin Dashboard

1. Get summary
2. Get revenue
3. Get top products
4. Get order status

---

## API Endpoints Summary

### Auth (`/api/v1/auth`)
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /login | Login (returns accessToken + refreshToken) | Public |
| POST | /register | Register customer | Public |
| POST | /refresh | Refresh token | Public |
| POST | /logout | Logout (revoke refresh token) | Public |
| POST | /logout-all | Logout all devices | Required |
| GET | /me | Get current user | Required |

### Token Refresh Flow
```
1. Login → Get accessToken + refreshToken
2. Use accessToken for API calls (15 min valid)
3. When 401 → Call /refresh with refreshToken
4. Receive new accessToken + new refreshToken
5. Old refreshToken is invalidated
```

### Products (`/api/v1`)
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /motorcycles | List motorcycles | Public |
| GET | /motorcycles/{id} | Product detail | Public |
| GET | /motorcycles/slug/{slug} | Product by slug | Public |
| GET | /brands | List brands | Public |
| GET | /categories | List categories | Public |

### Admin Products (`/api/v1/admin`)
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /motorcycles | Create motorcycle | ADMIN |
| PUT | /motorcycles/{id} | Update motorcycle | ADMIN |
| DELETE | /motorcycles/{id} | Delete motorcycle | ADMIN |
| POST | /brands | Create brand | ADMIN |
| POST | /categories | Create category | ADMIN |
| POST | /variants | Create variant | ADMIN |
| PUT | /variants/{id} | Update variant | ADMIN |
| DELETE | /variants/{id} | Delete variant | ADMIN |

### Cart (`/api/v1/cart`)
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /items | Add to cart | CUSTOMER |
| GET | / | Get cart | CUSTOMER |
| PUT | /items/{id} | Update cart item | CUSTOMER |
| DELETE | /items/{id} | Remove cart item | CUSTOMER |
| DELETE | /clear | Clear cart | CUSTOMER |

### Orders (`/api/v1/orders`)
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /checkout | Place order | CUSTOMER |
| GET | /my-orders | Get my orders | CUSTOMER |
| GET | /{orderId} | Order detail | CUSTOMER |
| PUT | /{orderId}/cancel | Cancel order | CUSTOMER |

### Staff Orders (`/api/v1/staff`)
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /orders | Get all orders | STAFF/ADMIN |
| GET | /orders/{orderId} | Order detail | STAFF/ADMIN |
| PUT | /orders/{orderId}/confirm | Confirm order | STAFF/ADMIN |
| PUT | /orders/{orderId}/preparing | Mark preparing | STAFF/ADMIN |
| PUT | /orders/{orderId}/shipping | Mark shipping | STAFF/ADMIN |
| PUT | /orders/{orderId}/delivered | Mark delivered | STAFF/ADMIN |
| PUT | /orders/{orderId}/cancel | Cancel order | STAFF/ADMIN |

### Admin Dashboard (`/api/v1/admin/dashboard`)
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /summary | Dashboard summary | ADMIN |
| GET | /revenue?range=7days | Revenue data | ADMIN |
| GET | /top-products | Top products | ADMIN |
| GET | /order-status | Order status counts | ADMIN |

---

## Environment Variables

| Variable | Default Value | Description |
|----------|---------------|-------------|
| baseUrl | http://localhost:8080 | API base URL |
| adminEmail | admin@honda.com | Admin login |
| adminPassword | Admin@123 | Admin password |
| staffEmail | staff@honda.com | Staff login |
| staffPassword | Staff@123 | Staff password |
| customerEmail | customer@test.com | Customer login |
| customerPassword | Customer@123 | Customer password |
| adminToken | (auto) | Access token for admin |
| adminRefreshToken | (auto) | Refresh token for admin |
| staffToken | (auto) | Access token for staff |
| staffRefreshToken | (auto) | Refresh token for staff |
| customerToken | (auto) | Access token for customer |
| customerRefreshToken | (auto) | Refresh token for customer |
| userId | (auto) | User ID after login |
| motorcycleId | 1 | Motorcycle ID for testing |
| variantId | 1 | Variant ID for testing |
| orderId | 1 | Order ID for testing |
| cartItemId | 1 | Cart item ID for testing |

---

## Example Test Scenarios

### Scenario 1: Customer Purchase Flow

```
1. Login as Customer
2. Get Products → Save productId
3. Get Product Detail
4. Add to Cart → Save cartItemId
5. Get Cart
6. Update Cart Item (quantity: 3)
7. Checkout → Save orderId
8. Get My Orders
9. Get Order Detail
10. Cancel Order
```

### Scenario 2: Staff Order Processing

```
1. Login as Staff
2. Get All Orders → Find pending order
3. Confirm Order
4. Preparing Order
5. Shipping Order
6. Delivered Order
```

### Scenario 3: Admin Dashboard Check

```
1. Login as Admin
2. Get Summary
3. Get Revenue (7days)
4. Get Top Products
5. Get Order Status
```

---

## Troubleshooting

### 401 Unauthorized
- Ensure you're logged in (tokens auto-saved after login)
- Check correct token variable for the endpoint

### 403 Forbidden
- Admin endpoints require ADMIN role
- Staff endpoints require STAFF or ADMIN role
- Cart/Order endpoints require CUSTOMER role

### 404 Not Found
- Check if application is running
- Verify baseUrl in environment

### 500 Internal Server Error
- Check Spring Boot console for errors
- Verify database is running

---

## Notes

- All protected endpoints require `Authorization: Bearer {{token}}` header
- Tokens auto-save after successful login requests
- IDs auto-save after creation requests for easy testing
- Response format is consistent: `{"code": 1000, "message": "Success", "result": {...}}`

---

## Files

```
docs/postman/
├── Honda-Dealership.postman_collection.json
├── Honda-Dealership.local.postman_environment.json
└── README-Postman.md
```