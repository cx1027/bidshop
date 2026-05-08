# UI Automation Test Plan

## Objective
This test plan defines the scope, approach, and priorities for UI automation testing of an e-commerce web application. The goal is to cover critical user journeys and high-value regressions using browser-based automation while keeping the scope practical and maintainable.

## Scope
The UI automation suite will focus on the following functional areas:

### User Account
- Sign up
- Sign in, and sign out.
- Email validation for sign up.

### Product Discovery
- Product information is correct, including title, price, images, variants, and availability.

### Search and Filtering by Category
- View by category(Bakery, Dairy, All categories).
- Keyword search behavior and result display(Full Cream Milk, bread)
- Search and Filtering by Category together works.(category:frozen+keyword:Cream, category:frozen+keyword:meat)
- Empty-state and no-result handling.(noodles)

### Shopping Cart
- Add to cart from product 'Shop' page.
- Update quantity, remove item, and cart summary refresh.
- Empty Cart should go Shop
- Continue to checkout

### Checkout and Payment
- Lack of one of the Shipping information, cannot be checkout.
- Full name, Email, Street address, City, Postcode is ready, the checkout works.

### Inventory Management
- The product number of in stock minus 1 after user bought the item.(eg. Free-Range Chicken Breast
, Puhoi Valley Greek Yoghurt)

## Out of Scope
The following are excluded from this plan:
- Cross-browser testing.
- Manual testing.
- Deep API testing, backend contract validation, and performance testing.
- Visual regression baselines unless explicitly requested.

## Tooling
The following tools are proposed for implementation and execution:
- Cursor for development workflow and authoring support.
- Selenium and Selenium MCP as the primary UI automation framework.
- Playwright agent as supporting tools for navigation, debugging, or execution assistance where useful.

## Approach
The automation suite should prioritize stable end-to-end user flows and core regression coverage rather than exhaustive UI checks. Tests should target business-critical paths, use reliable selectors, and avoid brittle assertions tied to layout or styling.

## Code Reusable Structure:
- Create a Features.md for the generated test cases list.
- Review the Features.md to find the code can be reused. eg. Login
- Update the Features.md when the test scripts passed.

## Test Data and Environment
- Use dedicated test accounts and stable seed data where possible.
- Use sandbox or mocked payment methods for checkout coverage.
- Keep environments predictable so failures are more likely to indicate product issues rather than test instability.

## Entry and Exit Criteria
Entry criteria:
- Test environment is accessible and sufficiently stable.
- Core user journeys are identified.
- Test accounts and required seed data are available.

Exit criteria:
- Critical smoke tests are automated and passing.
- Planned scope areas have baseline regression coverage.
- Known automation gaps and assumptions are documented.

## Risks and Assumptions
- UI automation may be affected by dynamic content, unstable selectors, third-party widgets, and changing test data.
- Payment and inventory flows may require mocks, seed control, or environment coordination.
- Since manual and cross-browser testing are out of scope, some usability and browser-specific issues may not be detected in this plan.

## Notes
This plan is intentionally focused on UI automation only. Based on the candidate instructions, API testing can be covered separately with tools such as Playwright API testing, Supertest with Jest, Supertest with Vitest, or similar, while UI automation is best implemented with Playwright or Cypress.
