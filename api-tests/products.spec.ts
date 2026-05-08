import { test, expect } from '@playwright/test';
import { assertStatus } from './api-client';

test.describe('Products API', () => {
  test.describe('GET /products', () => {
    test('200 returns catalogue with count and items', async ({ request }) => {
      const response = await request.get('/products');
      await assertStatus(response, 200);
      const json = await response.json();
      expect(json.count).toBeGreaterThan(0);
      expect(Array.isArray(json.items)).toBe(true);
      expect(json.items[0]).toMatchObject({
        id: expect.any(String),
        name: expect.any(String),
        description: expect.any(String),
        category: expect.any(String),
        price: expect.any(Number),
        unit: expect.any(String),
        stock: expect.any(Number),
        imageUrl: expect.any(String),
      });
    });

    test('200 filters by category and inStock', async ({ request }) => {
      const categoriesResponse = await request.get('/products/categories');
      await assertStatus(categoriesResponse, 200);
      const { categories } = await categoriesResponse.json();
      expect(categories.length).toBeGreaterThan(0);

      const filtered = await request.get(
        `/products?category=${encodeURIComponent(categories[0])}&inStock=true`,
      );
      await assertStatus(filtered, 200);
      const json = await filtered.json();
      expect(json.items.length).toBeGreaterThan(0);
      for (const item of json.items) {
        expect(item.category).toBe(categories[0]);
        expect(item.stock).toBeGreaterThan(0);
      }
    });

    test('200 supports search and price range filters', async ({ request }) => {
      const response = await request.get('/products?search=milk&minPrice=1&maxPrice=20');
      await assertStatus(response, 200);
      const json = await response.json();
      for (const item of json.items) {
        expect(item.price).toBeGreaterThanOrEqual(1);
        expect(item.price).toBeLessThanOrEqual(20);
      }
    });
  });

  test.describe('GET /products/categories', () => {
    test('200 returns array of category strings', async ({ request }) => {
      const response = await request.get('/products/categories');
      await assertStatus(response, 200);
      const json = await response.json();
      expect(Array.isArray(json.categories)).toBe(true);
      expect(json.categories.length).toBeGreaterThan(0);
      expect(typeof json.categories[0]).toBe('string');
    });
  });

  test.describe('GET /products/{id}', () => {
    test('200 returns full product object for valid id', async ({ request }) => {
      const response = await request.get('/products/p-001');
      await assertStatus(response, 200);
      const json = await response.json();
      expect(json).toMatchObject({
        id: 'p-001',
        name: expect.any(String),
        description: expect.any(String),
        category: expect.any(String),
        price: expect.any(Number),
        unit: expect.any(String),
        stock: expect.any(Number),
        imageUrl: expect.any(String),
      });
    });

    test('404 returns error for unknown product id', async ({ request }) => {
      const response = await request.get('/products/does-not-exist');
      await assertStatus(response, 404);
      const json = await response.json();
      expect(json.error).toBeTruthy();
    });
  });
});
