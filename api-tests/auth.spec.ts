import { test, expect } from '@playwright/test';
import { assertStatus, loginUser, registerUser, uniqueEmail } from './api-client';

test.describe('Auth API', () => {
  test('POST /auth/register – 201 returns token and user profile', async ({ request }) => {
    const email = uniqueEmail('register');
    const { body, token, user } = await registerUser(request, {
      email,
      name: 'Cheng Xiu',
    });

    expect(token).toBeTruthy();
    expect(user).toMatchObject({
      email: body.email,
      name: body.name,
    });
    expect(user.id).toBeTruthy();
  });

  test('POST /auth/register – 400 rejects invalid email', async ({ request }) => {
    const response = await request.post('/auth/register', {
      data: { email: 'not-an-email', password: 'secret1', name: 'Bad Email' },
    });
    await assertStatus(response, 400);
    const json = await response.json();
    expect(json.error).toBeTruthy();
  });

  test('POST /auth/register – 400 rejects short password', async ({ request }) => {
    const response = await request.post('/auth/register', {
      data: { email: uniqueEmail('short-pass'), password: '123', name: 'Short Password' },
    });
    await assertStatus(response, 400);
    const json = await response.json();
    expect(json.error).toBeTruthy();
  });

  test('POST /auth/register – 409 rejects duplicate email', async ({ request }) => {
    const email = uniqueEmail('duplicate');
    await registerUser(request, { email, password: 'secret1' });

    const response = await request.post('/auth/register', {
      data: { email, password: 'secret1', name: 'Duplicate User' },
    });
    await assertStatus(response, 409);
    const json = await response.json();
    expect(JSON.stringify(json).toLowerCase()).toContain('exist');
  });

  test('POST /auth/login – 200 returns token for valid credentials', async ({ request }) => {
    const email = uniqueEmail('login');
    await registerUser(request, { email, password: 'secret1', name: 'Login User' });

    const response = await loginUser(request, email, 'secret1');
    await assertStatus(response, 200);
    const json = await response.json();
    expect(json.token).toBeTruthy();
    expect(json.user).toMatchObject({ email, name: 'Login User' });
  });

  test('POST /auth/login – 400 rejects missing email', async ({ request }) => {
    const response = await request.post('/auth/login', {
      data: { password: 'secret1' },
    });
    await assertStatus(response, 400);
    const json = await response.json();
    expect(json.error).toBeTruthy();
  });

  test('POST /auth/login – 401 rejects wrong password', async ({ request }) => {
    const email = uniqueEmail('bad-pass');
    await registerUser(request, { email, password: 'correct1' });

    const response = await loginUser(request, email, 'wrongpass');
    await assertStatus(response, 401);
    const json = await response.json();
    expect(json.error).toBeTruthy();
  });

  test('POST /auth/login – 401 rejects unregistered email', async ({ request }) => {
    const response = await loginUser(request, 'nobody@example.com', 'wrongpass');
    await assertStatus(response, 401);
  });

  test('GET /auth/me – 200 returns current authenticated user', async ({ request }) => {
    const email = uniqueEmail('me');
    const { token } = await registerUser(request, { email, password: 'secret1', name: 'Me User' });

    const response = await request.get('/auth/me', {
      headers: { Authorization: `Bearer ${token}` },
    });
    await assertStatus(response, 200);
    const json = await response.json();
    expect(json).toMatchObject({ email, name: 'Me User' });
  });

  test('GET /auth/me – 401 rejects missing token', async ({ request }) => {
    const response = await request.get('/auth/me');
    await assertStatus(response, 401);
  });

  test('GET /auth/me – 401 rejects invalid token', async ({ request }) => {
    const response = await request.get('/auth/me', {
      headers: { Authorization: 'Bearer invalid.token.here' },
    });
    await assertStatus(response, 401);
  });
});
