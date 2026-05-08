# Bidshop Playwright API Tests

A Playwright-based API test suite targeting every endpoint and response code defined in the [OpenAPI spec](http://localhost:4000/api-docs) (`/openapi.json`).

## Test files

Each `.spec.ts` file corresponds to an OpenAPI tag:

| File | Tag | Endpoints covered |
|---|---|---|
| `health.spec.ts` | Health | `GET /health` |
| `auth.spec.ts` | Auth | `POST /auth/register`, `POST /auth/login`, `GET /auth/me` |
| `products.spec.ts` | Products | `GET /products`, `GET /products/categories`, `GET /products/{id}` |
| `cart.spec.ts` | Cart | `GET /cart`, `DELETE /cart`, `POST /cart/items`, `PATCH /cart/items/{productId}`, `DELETE /cart/items/{productId}` |
| `orders.spec.ts` | Orders | `POST /orders`, `GET /orders`, `GET /orders/{id}` |

Each endpoint is tested for **all documented response codes** (e.g. 200, 201, 400, 401, 404, 409). Tests are grouped under nested `test.describe` blocks keyed by operation for readability.

## Install

```bash
cd api-tests
npm install
npx playwright install
```

## Run

Start the backend first on `http://localhost:4000`, then run:

```bash
npm run test:api
```

Optional override:

```bash
API_BASE_URL=http://localhost:4000 npm run test:api
```
