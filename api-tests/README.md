# Bidshop API Test Suite

A Playwright-based API test suite targeting every endpoint and response code defined in the [OpenAPI spec](http://localhost:4000/api-docs) (`/openapi.json`).

## Framework choice

**Playwright** was selected for the following reasons:

1. **Purpose-built for API automation** — Playwright's `request` API provides a first-class HTTP client alongside its browser automation, making it equally capable for REST/SOAP endpoint testing without switching tools.
2. **Popular and well-supported** — with over 20k GitHub stars and strong corporate backing (Microsoft), Playwright has extensive documentation, active community support, and long-term stability.
3. **Suggested in the project README** — the parent `README.md` explicitly recommends Playwright for API test coverage.

---

## Test scope

Each `.spec.ts` file corresponds to an OpenAPI tag:

| File | Tag | Endpoints covered |
|---|---|---|
| `health.spec.ts` | Health | `GET /health` |
| `auth.spec.ts` | Auth | `POST /auth/register`, `POST /auth/login`, `GET /auth/me` |
| `products.spec.ts` | Products | `GET /products`, `GET /products/categories`, `GET /products/{id}` |
| `cart.spec.ts` | Cart | `GET /cart`, `DELETE /cart`, `POST /cart/items`, `PATCH /cart/items/{productId}`, `DELETE /cart/items/{productId}` |
| `orders.spec.ts` | Orders | `POST /orders`, `GET /orders`, `GET /orders/{id}` |

Each endpoint is tested for **all documented response codes** (e.g. 200, 201, 400, 401, 404, 409). Tests are grouped under nested `test.describe` blocks keyed by operation for readability.

---

## Install and run

### Prerequisites

- Node.js 18+
- npm 8+

### 1 — Install dependencies

```bash
cd api-tests
npm install
```

### 2 — Install browsers

```bash
npx playwright install
```

### 3 — Start the backend

```bash
# from the project root
cd backend
npm run dev
```

### 4 — Run the suite

```bash
cd api-tests
npx playwright test
```

This runs all 5 spec files against `http://localhost:4000`.

### Optional overrides

```bash
# Point to a different base URL
API_BASE_URL=http://localhost:4000 npx playwright test

# Run a single spec file
npx playwright test auth.spec.ts

# Run a single test by name
npx playwright test --grep "201 returns token"

# Run headed (opens a browser window)
npx playwright test --headed
```

---

## Trade-offs and things I would do differently with more time

### Manual testing with Postman

With more time, a comprehensive **Postman** should accompany the automated suite. 

### Other improvements

- **Data-driven tests**: select limited test cases to keep test suite concise.
- **Environment management**: implement a `.env`-based environment switcher (dev, staging, prod) so the same suite can target multiple backends without code changes.
