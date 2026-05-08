import { APIRequestContext, APIResponse, expect } from '@playwright/test';

export type RegisterPayload = {
  email: string;
  password: string;
  name: string;
};

export type CheckoutPayload = {
  customer: {
    name: string;
    email: string;
    address: string;
    city: string;
    postcode: string;
  };
};

export async function assertStatus(response: APIResponse, status: number) {
  expect(response.status(), await response.text()).toBe(status);
}

export function uniqueEmail(prefix = 'api-user') {
  return `${prefix}-${Date.now()}-${Math.random().toString(36).slice(2, 8)}@example.com`;
}

export async function registerUser(request: APIRequestContext, payload?: Partial<RegisterPayload>) {
  const body: RegisterPayload = {
    email: uniqueEmail(),
    password: 'secret1',
    name: 'Playwright User',
    ...payload,
  };

  const response = await request.post('/auth/register', { data: body });
  await assertStatus(response, 201);
  const json = await response.json();

  return {
    response,
    body,
    token: json.token as string,
    user: json.user as { id: string; email: string; name: string },
  };
}

export async function loginUser(request: APIRequestContext, email: string, password: string) {
  const response = await request.post('/auth/login', {
    data: { email, password },
  });
  return response;
}

export async function getAuthRequest(request: APIRequestContext, token: string, url: string) {
  return request.get(url, {
    headers: { Authorization: `Bearer ${token}` },
  });
}

export async function postAuthRequest(request: APIRequestContext, token: string, url: string, data?: unknown) {
  return request.post(url, {
    headers: { Authorization: `Bearer ${token}` },
    data,
  });
}

export async function patchAuthRequest(request: APIRequestContext, token: string, url: string, data?: unknown) {
  return request.patch(url, {
    headers: { Authorization: `Bearer ${token}` },
    data,
  });
}

export async function deleteAuthRequest(request: APIRequestContext, token: string, url: string) {
  return request.delete(url, {
    headers: { Authorization: `Bearer ${token}` },
  });
}
