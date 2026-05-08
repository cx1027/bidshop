import { test, expect } from '@playwright/test';
import { assertStatus } from './api-client';

test.describe('Health API', () => {
  test('GET /health returns 200 with status ok', async ({ request }) => {
    const response = await request.get('/health');
    await assertStatus(response, 200);
    const json = await response.json();
    expect(json).toMatchObject({
      status: 'ok',
      service: 'bidshop-api',
    });
    expect(json.time).toBeTruthy();
  });
});
