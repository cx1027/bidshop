# Bidshop UI Test Suite

Browser-based UI tests for the Bidshop frontend, targeting `http://localhost:5173` (configurable via `.env`).

## Framework choice

**Selenium WebDriver 4.24.0** with **TestNG 7.10.2** and **Java 17** (Firefox/GeckoDriver, managed by [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)).

Selenium was selected over Playwright for this suite because it gives direct access to the application's DOM and JavaScript execution context, making it a more effective tool for **evaluating code quality and identifying frontend defects** — particularly around DOM structure, accessibility attributes (`data-testid`), and JavaScript behaviour that a higher-level tool might obscure. It is also battle-proven and stable across a wide range of browser and server configurations.

---

## Test cases coverage

All tests target the frontend at `http://localhost:5173`. The suite uses `data-testid` CSS selectors throughout for locator stability.

### 1. Sign Up (`tests.SignUpTests`)

| # | Test |
|---|---|
| 1 | Registers with valid credentials and navigates to products page |
| 2 | Shows error / stays on page with a password shorter than 6 characters |
| 3 | Stays on `/register` when Full Name is omitted |

### 2. Sign In (`tests.SignInTests`)

| # | Test |
|---|---|
| 1 | Signs in with valid credentials and navbar shows authenticated state |
| 2 | Shows error for an unregistered email address |
| 3 | Shows error for a wrong password |

### 3. Email Validation (`tests.EmailValidationTests`)

| # | Test |
|---|---|
| 1 | Rejects email missing the `@` symbol |
| 2 | Rejects email missing the local-part (e.g. `@example.com`) |
| 3 | Accepts a well-formed unique email and redirects to `/` |

### 4. Product Discovery (`tests.ProductDiscoveryTests`)

| # | Test |
|---|---|
| 1 | Opens product grid, selects a known product, and verifies name, category, description, price, unit, and stock are all correct |

### 5. Search and Filter (`tests.SearchAndFilterTests`)

| # | Test |
|---|---|
| 1 | Selecting a category shows only products from that category; clearing returns all products |
| 2 | Searching by keyword shows only products whose description contains the keyword |
| 3 | Applying category filter AND keyword search together uses AND logic |
| 4 | Searching a term with no matches shows an empty-state message |

**Total: 14 UI tests across 5 test classes.**

---

## Install and run

### Prerequisites

- Java 17+
- Maven 3.8+

### 1 — Install dependencies

```bash
cd ui-tests
mvn dependency:resolve
```

### 2 — Configure the base URL

Create a `.env` file in `ui-tests/` (it is gitignored):

```env
BASEURL=http://localhost:5173
```

If no `.env` is present, the suite defaults to `http://localhost:5173`.

### 3 — Start the frontend

```bash
cd frontend
npm run dev
```

### 4 — Run the full suite

```bash
cd ui-tests
mvn test
```

This runs `src/test/resources/testng.xml`, which registers `base.TestListener` for screenshot-on-failure and executes all 5 test classes in sequence (parallel methods, thread-count 1).

### Optional flags

**Headless mode** (no browser window opens):

```bash
mvn test -Dheadless=true
```

**Run a single test class:**

```bash
mvn test -Dtest=SignUpTests
```

**Run a single test method:**

```bash
mvn test -Dtest=SignUpTests#signUpWithValidCredentialsNavigatesToProducts
```

**Run with TestNG's built-in reporter HTML:**

```bash
mvn test test-output  # opens test-output/index.html after run
```

Screenshots from failed tests are saved to `target/screenshots/<TestName>.png`.

---

## What I would do differently with more time

### 1 — Better organised code structure
### 2 — Data-driven testing
### 3 — Improved code readability
### 4 — Exploring Playwright agent and CLI