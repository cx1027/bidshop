import { test, expect } from '@playwright/test';
import {
  assertStatus,
  deleteAuthRequest,
  getAuthRequest,
  postAuthRequest,
  registerUser,
} from './api-client';

const VALID_ORDER = {
  customer: {
    name: 'Cheng Xiu',
    email: 'cheng@example.com',
    address: '1 Queen Street',
    city: 'Auckland',
    postcode: '1010',
  },
};

test.describe('Orders API', () => {
  test.describe('POST /orders', () => {
    test('201 places order, decrements stock, and clears cart', async ({ request }) => {
      const { token } = await registerUser(request);

      const before = await request.get('/products/p-001');
      const { stock: beforeStock } = await before.json();

      await postAuthRequest(request, token, '/cart/items', { productId: 'p-001', quantity: 2 });

      const response = await postAuthRequest(request, token, '/orders', VALID_ORDER);
      await assertStatus(response, 201);
      const order = await response.json();

      expect(order).toMatchObject({
        status: 'CONFIRMED',
        customer: VALID_ORDER.customer,
      });
      expect(order.items).toHaveLength(1);
      expect(order.subtotal).toBeGreaterThan(0);
      expect(order.gst).toBeGreaterThan(0);
      expect(order.total).toBeCloseTo(order.subtotal + order.gst, 2);

      const after = await request.get('/products/p-001');
      const { stock: afterStock } = await after.json();
      expect(afterStock).toBe(beforeStock - 2);

      const cart = await getAuthRequest(request, token, '/cart');
      const cartJson = await cart.json();
      expect(cartJson.items).toHaveLength(0);
    });

    test('400 rejects empty cart', async ({ request }) => {
      const { token } = await registerUser(request);

      const response = await postAuthRequest(request, token, '/orders', VALID_ORDER);
      await assertStatus(response, 400);
      const json = await response.json();
      expect(json.error).toBeTruthy();
    });

    test('400 rejects invalid postcode', async ({ request }) => {
      const { token } = await registerUser(request);
      await postAuthRequest(request, token, '/cart/items', { productId: 'p-001', quantity: 1 });

      const response = await postAuthRequest(request, token, '/orders', {
        customer: { ...VALID_ORDER.customer, postcode: '123' },
      });
      await assertStatus(response, 400);
      const json = await response.json();
      expect(json.error).toBeTruthy();
    });

    test('401 rejects unauthenticated request', async ({ request }) => {
      const response = await request.post('/orders', { data: VALID_ORDER });
      await assertStatus(response, 401);
    });
  });

  test.describe('GET /orders', () => {
    test('200 returns orders list with count and items', async ({ request }) => {
      const { token } = await registerUser(request);
      await postAuthRequest(request, token, '/cart/items', { productId: 'p-001', quantity: 1 });
      const { id: orderId } = (await (await postAuthRequest(request, token, '/orders', VALID_ORDER)).json()) as { id: string };

      const response = await getAuthRequest(request, token, '/orders');
      await assertStatus(response, 200);
      const json = await response.json();
      expect(typeof json.count).toBe('number');
      expect(Array.isArray(json.items)).toBe(true);
      expect(json.items.some((o: { id: string }) => o.id === orderId)).toBe(true);
    });

    test('401 rejects unauthenticated request', async ({ request }) => {
      const response = await request.get('/orders');
      await assertStatus(response, 401);
    });
  });

  test.describe('GET /orders/{id}', () => {
    test('200 returns order details for own order', async ({ request }) => {
      const { token } = await registerUser(request);
      await postAuthRequest(request, token, '/cart/items', { productId: 'p-001', quantity: 1 });
      const { id: orderId } = (await (await postAuthRequest(request, token, '/orders', VALID_ORDER)).json()) as { id: string };

      const response = await getAuthRequest(request, token, `/orders/${orderId}`);
      await assertStatus(response, 200);
      const order = await response.json();
      expect(order.id).toBe(orderId);
      expect(order.status).toBe('CONFIRMED');
    });

    test('401 rejects unauthenticated request', async ({ request }) => {
      const response = await request.get('/orders/some-uuid');
      await assertStatus(response, 401);
    });

    test('404 returns error for unknown order id', async ({ request }) => {
      const { token } = await registerUser(request);

      const response = await getAuthRequest(request, token, '/orders/00000000-0000-0000-0000-000000000000');
      await assertStatus(response, 404);
      const json = await response.json();
      expect(json.error).toBeTruthy();
    });
  });

  test.describe('DELETE /cart', () => {
    test('200 clears cart (used by order placement)', async ({ request }) => {
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
  });
});
