# Features.md — BidShop UI Automation Test Cases

> Created from `UI_Automation_Test_Plan_v2.md` lines 9-12 (User Account).
> Reusable page objects: `SignUpPage`, `SignInPage`, `NavBar`.

---

## User Account

### Sign Up

| Test ID | Feature | Test Case | Status |
|---------|---------|-----------|--------|
| SU-001 | Sign Up | Verify that a user can successfully sign up with valid credentials | PASS |
| SU-002 | Sign Up | Verify that a user cannot register with the same email twice | PASS |
| SU-003 | Sign Up | Verify that a user cannot sign up with a password shorter than 6 characters | PASS |
| SU-004 | Sign Up | Verify that navigating from register to login page works correctly | PASS |
| SU-005 | Sign Up | Verify that a user can sign up with minimum valid name (single character) | PASS |

### Sign In and Sign Out

| Test ID | Feature | Test Case | Status |
|---------|---------|-----------|--------|
| SI-001 | Sign In | Verify that a user can successfully sign in with valid credentials | PASS |
| SI-002 | Sign In | Verify that signing in with an unregistered email shows an error | PASS |
| SI-003 | Sign In | Verify that signing in with wrong password shows an error | PASS |
| SI-004 | Sign Out | Verify that a logged-in user can sign out successfully | PASS |
| SI-005 | Sign In | Verify that navigating from login to register page works correctly | PASS |
| SI-006 | Sign In | Verify that signed-out user can sign in again after signing out | PASS |

### Email Validation for Sign Up

| Test ID | Feature | Test Case | Status |
|---------|---------|-----------|--------|
| EV-001 | Email Validation | Verify that registration fails with an empty email field | PASS |
| EV-002 | Email Validation | Verify that registration fails with an invalid email format (missing @) | PASS |
| EV-003 | Email Validation | Verify that registration fails with an invalid email format (missing domain) | PASS |
| EV-004 | Email Validation | Verify that registration fails with an invalid email format (missing username) | PASS |
| EV-005 | Email Validation | Verify that registration fails with an invalid email format (no TLD) | PASS |
| EV-006 | Email Validation | Verify that registration fails with spaces in email | PASS |
| EV-007 | Email Validation | Verify that registration succeeds with a valid email format | PASS |

---

## Reusable Components

### Page Objects

| Class | File | Purpose |
|-------|------|---------|
| `SignUpPage` | `pages/SignUpPage.java` | Handles registration form interactions: name, email, password fields, submit button, error message display, and navigation link to login |
| `SignInPage` | `pages/SignInPage.java` | Handles login form interactions: email, password fields, submit button, error message display, and navigation link to register |
| `NavBar` | `pages/NavBar.java` | Handles navigation and auth state: home, products, cart links; login/register buttons when logged out; logout button and username when logged in |

### Base Classes

| Class | File | Purpose |
|-------|------|---------|
| `BaseTest` | `base/BaseTest.java` | TestNG base class; initializes Firefox WebDriver per test method with configurable headless mode |
| `BasePage` | `base/BasePage.java` | Page object base; delegates all element interactions to `WaitUtilsKeywords` for reliable synchronization |
| `TestListener` | `base/TestListener.java` | TestNG listener; captures screenshots on test failures for debugging |

### Utility Classes

| Class | File | Purpose |
|-------|------|---------|
| `DriverFactory` | `utils/DriverFactory.java` | Thread-safe Firefox WebDriver factory using WebDriverManager for browser driver management |
| `WaitUtilsKeywords` | `utils/WaitUtilsKeywords.java` | Centralized explicit wait keywords for click, type, getText, getAttribute, element presence/visibility, scroll, URL wait, and alert handling |
| `ConfigReader` | `utils/ConfigReader.java` | Reads `config.properties` for base URL and timeout settings |

---

*Last updated: After initial test run — all 18 tests passed.*
