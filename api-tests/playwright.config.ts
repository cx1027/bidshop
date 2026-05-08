import { defineConfig } from '@playwright/test';

const baseURL = process.env.API_BASE_URL ?? 'http://localhost:4000';

export default defineConfig({
  testDir: '.',
  fullyParallel: false,
  timeout: 30_000,
  expect: {
    timeout: 5_000,
  },
  use: {
    baseURL,
    extraHTTPHeaders: {
      Accept: 'application/json',
    },
  },
  reporter: [['list']],
});
