import { test, expect } from '@playwright/test';
import {
  assertStatus,
  deleteAuthRequest,
  getAuthRequest,
  patchAuthRequest,
  postAuthRequest,
  registerUser,
} from './api-client';

test.describe('Cart API', () => {
  test.describe('GET /cart', () => {
    test('200 returns cart with items, subtotal, gst, total', async ({ request }) => {
      const { token } = await registerUser(request);

      const response = await getAuthRequest(request, token, '/cart');
      await assertStatus(response, 200);
      const cart = await response.json();

      expect(cart.userId).toBeTruthy();
      expect(Array.isArray(cart.items)).toBe(true);
      expect(typeof cart.subtotal).toBe('number');
      expect(typeof cart.gst).toBe('number');
      expect(typeof cart.total).toBe('number');
      expect(cart.updatedAt).toBeTruthy();
    });

    test('401 rejects unauthenticated request', async ({ request }) => {
      const response = await request.get('/cart');
      await assertStatus(response, 401);
    });
  });

  test.describe('DELETE /cart', () => {
    test('200 clears cart and returns empty cart', async ({ request }) => {
      const { token } = await registerUser(request);
      await postAuthRequest(request, token, '/cart/items', { productId: 'p-001', quantity: 1 });

      const response = await request.delete('/cart', {
        headers: { Authorization: `Bearer ${token}` },
      });
      await assertStatus(response, 200);
      const cart = await response.json();
      expect(cart.items).toHaveLength(0);
      expect(cart.total).toBe(0);
    });

    test('401 rejects unauthenticated request', async ({ request }) => {
      const response = await request.delete('/cart');
      await assertStatus(response, 401);
    });
  });

  test.describe('POST /cart/items', () => {
    test('201 adds item and returns updated cart', async ({ request }) => {
      const { token } = await registerUser(request);

      const response = await postAuthRequest(request, token, '/cart/items', {
        productId: 'p-001',
        quantity: 2,
      });
      await assertStatus(response, 201);
      const cart = await response.json();

      expect(cart.items.some((i: { productId: string }) => i.productId === 'p-001')).toBe(true);
      expect(cart.subtotal).toBeGreaterThan(0);
    });

    test('400 rejects missing productId', async ({ request }) => {
      const { token } = await registerUser(request);

      const response = await postAuthRequest(request, token, '/cart/items', {
        quantity: 2,
      });
      await assertStatus(response, 400);
      const json = await response.json();
      expect(json.error).toBeTruthy();
    });

    test('401 rejects unauthenticated request', async ({ request }) => {
      const response = await request.post('/cart/items', {
        data: { productId: 'p-001', quantity: 1 },
      });
      await assertStatus(response, 401);
    });

    test('404 rejects non-existent product', async ({ request }) => {
      const { token } = await registerUser(request);

      const response = await postAuthRequest(request, token, '/cart/items', {
        productId: 'does-not-exist',
        quantity: 1,
      });
      await assertStatus(response, 404);
    });
  });

  test.describe('PATCH /cart/items/{productId}', () => {
    test('200 updates quantity and returns updated cart', async ({ request }) => {
      const { token } = await registerUser(request);
      await postAuthRequest(request, token, '/cart/items', { productId: 'p-001', quantity: 1 });

      const response = await patchAuthRequest(request, token, '/cart/items/p-001', { quantity: 5 });
      await assertStatus(response, 200);
      const cart = await response.json();
      const item = cart.items.find((i: { productId: string }) => i.productId === 'p-001');
      expect(item.quantity).toBe(5);
    });

    test('400 rejects quantity of 0', async ({ request }) => {
      const { token } = await registerUser(request);
      await postAuthRequest(request, token, '/cart/items', { productId: 'p-001', quantity: 1 });

      const response = await patchAuthRequest(request, token, '/cart/items/p-001', { quantity: 0 });
      await assertStatus(response, 400);
    });

    test('401 rejects unauthenticated request', async ({ request }) => {
      const response = await request.patch('/cart/items/p-001', {
        data: { quantity: 5 },
      });
      await assertStatus(response, 401);
    });

    test('404 rejects item not in cart', async ({ request }) => {
      const { token } = await registerUser(request);

      const response = await patchAuthRequest(request, token, '/cart/items/p-001', { quantity: 5 });
      await assertStatus(response, 404);
    });
  });

  test.describe('DELETE /cart/items/{productId}', () => {
    test('200 removes item and returns updated cart', async ({ request }) => {
      const { token } = await registerUser(request);
      await postAuthRequest(request, token, '/cart/items', { productId: 'p-001', quantity: 1 });

      const response = await deleteAuthRequest(request, token, '/cart/items/p-001');
      await assertStatus(response, 200);
      const cart = await response.json();
      expect(cart.items.some((i: { productId: string }) => i.productId === 'p-001')).toBe(false);
    });

    test('401 rejects unauthenticated request', async ({ request }) => {
      const response = await request.delete('/cart/items/p-001');
      await assertStatus(response, 401);
    });

    test('404 rejects item not in cart', async ({ request }) => {
      const { token } = await registerUser(request);

      const response = await deleteAuthRequest(request, token, '/cart/items/p-001');
      await assertStatus(response, 404);
    });
  });
});
