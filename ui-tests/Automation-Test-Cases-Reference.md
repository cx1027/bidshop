# Bidshop Homepage — Manual Test Cases

> **Project:** Bidshop E-commerce Platform
> **Page Under Test:** Homepage (`/`)
> **Frontend:** http://localhost:5173
> **Generated:** 2026-05-07

---

## Page Overview

The homepage is the main product listing page of Bidshop — a New Zealand food-service marketplace. It displays 18 products across 8 categories in a card grid, with a search bar and category filter above the grid.

### Element Inventory (from snapshot)

| Element | Type | Notes |
|---------|------|-------|
| Bidshop logo (link) | Navigation | Text "B Bidshop", points to `/` |
| Shop nav link | Navigation | Points to `/` |
| Cart nav link | Navigation | Points to `/cart` |
| Log in link | Navigation | Points to `/login` |
| Register link | Navigation | Points to `/register` |
| Hero H1 heading | Content | "Fresh food, delivered to your kitchen." |
| Hero paragraph | Content | Company description text |
| Search input | Searchbox | Label "Search products…" |
| Category dropdown | Combobox | "All categories" + 8 category options |
| Products count | Text | "18 products" |
| Product cards (×18) | Cards | Each with image, category, name, description, price, stock, CTA |
| Footer copyright | Footer | "© 2026 Bidshop demo – built for the Bidfood SDET technical test." |

### Product Catalogue (18 Products)

| ID | Name | Category | Price | Unit | Stock |
|----|------|----------|-------|------|-------|
| p-001 | NZ Grass-Fed Beef Mince | Meat & Poultry | $14.50 | 500g | 40 |
| p-002 | Free-Range Chicken Breast | Meat & Poultry | $18.90 | 1kg | 35 |
| p-003 | Bluff Oysters (Dozen) | Seafood | $39.95 | dozen | 10 |
| p-004 | Wild NZ King Salmon Fillet | Seafood | $44.00 | 1kg | 15 |
| p-005 | Organic Hass Avocados | Fresh Produce | $9.50 | 6-pack | 60 |
| p-006 | Baby Spinach Leaves | Fresh Produce | $4.50 | 200g | 80 |
| p-007 | Vine-Ripened Tomatoes | Fresh Produce | $6.99 | 1kg | 50 |
| p-008 | Anchor Full Cream Milk | Dairy | $5.40 | 2L | 100 |
| p-009 | Mainland Tasty Cheese Block | Dairy | $12.90 | 500g | 45 |
| p-010 | Puhoi Valley Greek Yoghurt | Dairy | $7.20 | 500g | 30 |
| p-011 | Vogel's Mixed Grain Bread | Bakery | $5.99 | 750g | 40 |
| p-012 | Sourdough Ciabatta | Bakery | $6.50 | each | 25 |
| p-013 | Watties Baked Beans | Pantry | $2.80 | 420g | 200 |
| p-014 | Extra Virgin Olive Oil | Pantry | $18.50 | 500ml | 30 |
| p-015 | Tip Top Vanilla Ice Cream | Frozen | $9.90 | 2L | 50 |
| p-016 | Frozen Mixed Berries | Frozen | $13.50 | 1kg | 40 |
| p-017 | Phoenix Organic Cola | Beverages | $11.90 | 6-pack | 60 |
| p-018 | Karma Cola Sparkling Water | Beverages | $14.50 | 12-pack | 40 |

---

## TC-HP-001: Homepage — Full Catalogue Load

**Priority:** P0 (Critical)
**Type:** Functional
**Module:** Homepage — Initial Load
**Estimated Time:** 3 minutes

### Objective
Verify the homepage loads with all 18 products displayed and all UI elements render correctly.

### Preconditions
- Frontend running on port 5173
- Backend running on port 4000 with seed data loaded
- User is logged out (no `bidshop.token` in localStorage)

### Test Steps

1. Navigate to `http://localhost:5173/`
   **Expected:** Page loads with HTTP 200, no blank screen or error message

2. Verify the header banner renders:
   - Bidshop logo text "B Bidshop" visible, links to `/`
   - "Shop" nav link visible, links to `/`
   - "Cart" nav link visible, links to `/cart`
   - "Log in" link visible, links to `/login`
   - "Register" link visible, links to `/register`

3. Verify the hero section renders:
   - H1 heading: "Fresh food, delivered to your kitchen."
   - Paragraph: "Bidshop supplies cafes, restaurants and foodservice businesses across Aotearoa with quality ingredients from trusted local suppliers."

4. Verify the search and filter bar renders:
   - Search input labeled "Search products…" is present and empty
   - Category dropdown present with "All categories" selected by default
   - Products count text reads "18 products"

5. Verify the product grid:
   - Exactly 18 product cards are displayed
   - Each card contains: product image, category label, product name (H3), description, price with unit, stock count, "Log in to buy" link

6. Verify the footer renders:
   - Copyright text: "© 2026 Bidshop demo – built for the Bidfood SDET technical test."

7. Open DevTools → Network tab, refresh the page
   **Expected:** API calls `GET /products` and `GET /products/categories` return 200

### Post-conditions
- No data modified; page state unchanged

---

## TC-HP-002: Homepage — Product Card Content Accuracy

**Priority:** P0 (Critical)
**Type:** Functional
**Module:** Homepage — Product Cards
**Estimated Time:** 5 minutes

### Objective
Verify each of the 18 product cards displays correct product information.

### Preconditions
- Homepage loaded with all 18 products visible

### Test Steps

For each product, verify the card contains accurate data:

| ID | Check Category Label | Check Product Name | Check Price | Check Unit | Check Stock |
|----|----------------------|--------------------|-------------|------------|-------------|
| p-001 | Meat & Poultry | NZ Grass-Fed Beef Mince | $14.50 | / 500g | 40 in stock |
| p-002 | Meat & Poultry | Free-Range Chicken Breast | $18.90 | / 1kg | 35 in stock |
| p-003 | Seafood | Bluff Oysters (Dozen) | $39.95 | / dozen | 10 in stock |
| p-004 | Seafood | Wild NZ King Salmon Fillet | $44.00 | / 1kg | 15 in stock |
| p-005 | Fresh Produce | Organic Hass Avocados | $9.50 | / 6-pack | 60 in stock |
| p-006 | Fresh Produce | Baby Spinach Leaves | $4.50 | / 200g | 80 in stock |
| p-007 | Fresh Produce | Vine-Ripened Tomatoes | $6.99 | / 1kg | 50 in stock |
| p-008 | Dairy | Anchor Full Cream Milk | $5.40 | / 2L | 100 in stock |
| p-009 | Dairy | Mainland Tasty Cheese Block | $12.90 | / 500g | 45 in stock |
| p-010 | Dairy | Puhoi Valley Greek Yoghurt | $7.20 | / 500g | 30 in stock |
| p-011 | Bakery | Vogel's Mixed Grain Bread | $5.99 | / 750g | 40 in stock |
| p-012 | Bakery | Sourdough Ciabatta | $6.50 | / each | 25 in stock |
| p-013 | Pantry | Watties Baked Beans | $2.80 | / 420g | 200 in stock |
| p-014 | Pantry | Extra Virgin Olive Oil | $18.50 | / 500ml | 30 in stock |
| p-015 | Frozen | Tip Top Vanilla Ice Cream | $9.90 | / 2L | 50 in stock |
| p-016 | Frozen | Frozen Mixed Berries | $13.50 | / 1kg | 40 in stock |
| p-017 | Beverages | Phoenix Organic Cola | $11.90 | / 6-pack | 60 in stock |
| p-018 | Beverages | Karma Cola Sparkling Water | $14.50 | / 12-pack | 40 in stock |

### Additional Checks (All Cards)
- [ ] Product image is present and not broken (no missing image icon)
- [ ] Category label is styled distinctly (uppercase or badge-style)
- [ ] Product name is rendered as a heading (H3)
- [ ] Price is prefixed with "$" symbol
- [ ] Stock count is visible on every card
- [ ] "Log in to buy" link is present and clickable on every card

### Post-conditions
- No data modified

---

## TC-HP-003: Homepage — Search by Product Name

**Priority:** P0 (Critical)
**Type:** Functional
**Module:** Homepage — Search
**Estimated Time:** 4 minutes

### Objective
Verify the search box filters products by name with debounce delay.

### Preconditions
- Homepage loaded with all 18 products visible

### Test Steps

**Test 3A: Exact Name Match**
1. Type `"beef"` in the search input
   **Expected:** No immediate change (200ms debounce)
2. Wait 300ms
   **Expected:** Only "NZ Grass-Fed Beef Mince" appears (1 result), count updates to "1 product"

**Test 3B: Partial Name Match**
1. Clear the search input
2. Type `"chicken"`
   **Expected:** Only "Free-Range Chicken Breast" appears

**Test 3C: Case-Insensitive Search**
1. Clear the search input
2. Type `"SALMON"` (all uppercase)
   **Expected:** "Wild NZ King Salmon Fillet" appears (case-insensitive match)

**Test 3D: Multi-Word Search**
1. Clear the search input
2. Type `"olive oil"`
   **Expected:** "Extra Virgin Olive Oil" appears

**Test 3E: No Matching Results**
1. Clear the search input
2. Type `"xyznotfound123"`
   **Expected:** Empty grid, count shows "0 products", no error message

**Test 3F: Search Clears on Empty**
1. Type `"salmon"`, verify 1 result
2. Clear the search input completely (delete all text)
   **Expected:** All 18 products return

### Post-conditions
- Search input empty, all 18 products visible

---

## TC-HP-004: Homepage — Search by Description Keyword

**Priority:** P1 (High)
**Type:** Functional
**Module:** Homepage — Search
**Estimated Time:** 3 minutes

### Objective
Verify search matches product descriptions (not just names).

### Preconditions
- Homepage loaded with all 18 products visible

### Test Steps

**Test 4A: Keyword in Description Only**
1. Type `"organic"`
   **Expected:** Products with "organic" in their description appear (at minimum: Organic Hass Avocados, Phoenix Organic Cola)

**Test 4B: Keyword Across Name and Description**
1. Clear and type `"fresh"`
   **Expected:** Multiple products matching in name or description appear

**Test 4C: Location Keyword**
1. Type `"Canterbury"`
   **Expected:** "NZ Grass-Fed Beef Mince" appears (description mentions Canterbury)

**Test 4D: Brand/Producer Keyword**
1. Type `"Anchor"`
   **Expected:** "Anchor Full Cream Milk" appears

### Post-conditions
- Search input cleared

---

## TC-HP-005: Homepage — Category Filter

**Priority:** P0 (Critical)
**Type:** Functional
**Module:** Homepage — Category Filter
**Estimated Time:** 5 minutes

### Objective
Verify the category dropdown correctly filters the product grid.

### Preconditions
- Homepage loaded with all 18 products visible

### Test Steps

**Test 5A: Filter by Each Category**
For each dropdown option, select and verify the count and product names:

| Category Option | Expected Count | Expected Products |
|-----------------|----------------|-------------------|
| All categories | 18 | All products |
| Bakery | 2 | Vogel's Mixed Grain Bread, Sourdough Ciabatta |
| Beverages | 2 | Phoenix Organic Cola, Karma Cola Sparkling Water |
| Dairy | 3 | Anchor Full Cream Milk, Mainland Tasty Cheese Block, Puhoi Valley Greek Yoghurt |
| Fresh Produce | 3 | Organic Hass Avocados, Baby Spinach Leaves, Vine-Ripened Tomatoes |
| Frozen | 2 | Tip Top Vanilla Ice Cream, Frozen Mixed Berries |
| Meat & Poultry | 2 | NZ Grass-Fed Beef Mince, Free-Range Chicken Breast |
| Pantry | 2 | Watties Baked Beans, Extra Virgin Olive Oil |
| Seafood | 2 | Bluff Oysters (Dozen), Wild NZ King Salmon Fillet |

**Test 5B: Reset to All Categories**
1. Select "Meat & Poultry" → verify 2 products
2. Change dropdown back to "All categories"
   **Expected:** All 18 products displayed

### Post-conditions
- Dropdown set to "All categories"

---

## TC-HP-006: Homepage — Search and Category Filter Combined

**Priority:** P1 (High)
**Type:** Functional
**Module:** Homepage — Combined Filtering
**Estimated Time:** 3 minutes

### Objective
Verify search and category filters work together (AND logic).

### Preconditions
- Homepage loaded with all 18 products visible

### Test Steps

**Test 6A: Category + Search narrows results**
1. Select category "Seafood"
   **Expected:** 2 products displayed
2. Type `"salmon"` in search
   **Expected:** Only "Wild NZ King Salmon Fillet" appears (1 result)

**Test 6B: Search within category yields nothing**
1. Select category "Seafood"
2. Type `"chicken"` in search
   **Expected:** 0 products, "0 products" count shown

**Test 6C: Clearing search keeps category filter**
1. Select "Seafood", type "salmon" → 1 result
2. Clear the search input
   **Expected:** Returns to 2 Seafood products (category still active)

**Test 6D: Clearing category resets to search results**
1. Select "All categories", type `"avocado"`
   **Expected:** "Organic Hass Avocados" (1 result)
2. Change category to "Fresh Produce"
   **Expected:** Still 1 result (Organic Hass Avocados)

**Test 6E: Both filters cleared**
1. Set category to "Seafood", search to "salmon"
2. Clear both filters (reset dropdown to "All categories" and clear search)
   **Expected:** All 18 products displayed

### Post-conditions
- All filters cleared; 18 products visible

---

## TC-HP-007: Homepage — Product Card CTA for Guest User

**Priority:** P0 (Critical)
**Type:** Functional
**Module:** Homepage — Product Card CTA
**Estimated Time:** 3 minutes

### Objective
Verify product cards show "Log in to buy" for unauthenticated users and the link navigates correctly.

### Preconditions
- User is logged out (no `bidshop.token` in localStorage)
- Browser cookies cleared

### Test Steps

**Test 7A: CTA Text for All Products**
1. Verify every product card shows "Log in to buy" (not "Add to cart")
   **Expected:** All 18 cards show "Log in to buy" link

**Test 7B: CTA Navigation**
1. Click "Log in to buy" on any product card (e.g., p-001)
   **Expected:** Browser navigates to `/login`

**Test 7C: Redirect After Login**
1. On the login page, enter valid credentials and log in
   **Expected:** User redirected back to homepage (`/`) and is logged in
2. Verify the same product card now shows "Add to cart" (or different CTA for authenticated users)

### Post-conditions
- User is logged in

---

## TC-HP-008: Homepage — Navigation Links

**Priority:** P0 (Critical)
**Type:** Functional
**Module:** Homepage — Navigation
**Estimated Time:** 3 minutes

### Objective
Verify all navigation links in the header navigate to the correct routes.

### Preconditions
- Homepage loaded

### Test Steps

**Test 8A: Bidshop Logo**
1. Click "B Bidshop" logo
   **Expected:** Navigates to `/` (homepage)

**Test 8B: Shop Link**
1. Click "Shop" nav link
   **Expected:** Navigates to `/` (stays on homepage)

**Test 8C: Cart Link (Guest)**
1. Observe cart link in nav
   **Expected:** Link points to `/cart`
2. Click the Cart link
   **Expected:** Since user is not logged in, redirects to `/login?redirect=/cart` or similar

**Test 8D: Log in Link**
1. Click "Log in" link
   **Expected:** Navigates to `/login`

**Test 8E: Register Link**
1. Click "Register" link
   **Expected:** Navigates to `/register`

### Post-conditions
- No state modified

---

## TC-HP-009: Homepage — Product Card Interactions (Logged In)

**Priority:** P1 (High)
**Type:** Functional
**Module:** Homepage — Product Card (Authenticated)
**Estimated Time:** 5 minutes

### Objective
Verify "Add to cart" functionality on product cards for authenticated users.

### Preconditions
- User is logged in with a valid token in localStorage

### Test Steps

**Test 9A: Add to Cart — Single Item**
1. On any product card, click "Add to cart"
   **Expected:** Button text changes to "Adding..." briefly, then "Added to cart"
2. Verify the cart badge in the navbar shows "1"
3. Navigate to `/cart`
   **Expected:** Product appears in cart with correct name, price, and quantity 1

**Test 9B: Add Same Item Again (Quantity Stack)**
1. Navigate back to homepage
2. Click "Add to cart" on the same product again
   **Expected:** Cart badge increments to "2" (or quantity on existing item increases)
3. Go to cart
   **Expected:** Single row for the product with quantity 2, not two separate rows

**Test 9C: Add Multiple Different Products**
1. Navigate to homepage
2. Add p-001 (Beef Mince) to cart
3. Add p-005 (Avocados) to cart
   **Expected:** Cart badge shows "2" (or total quantity)
4. Verify cart contains both products as separate rows

**Test 9D: Add to Cart Button States**
1. Observe the add-to-cart button before clicking
   **Expected:** Button text "Add to cart"
2. Click and hold or watch for loading state
   **Expected:** Loading indicator or text change during API call
3. After adding, verify button returns to normal or shows confirmation

### Post-conditions
- User logged in, cart contains added items

---

## TC-HP-010: Homepage — Responsive Layout

**Priority:** P1 (High)
**Type:** UI/Visual
**Module:** Homepage — Responsive Design
**Estimated Time:** 5 minutes

### Objective
Verify the homepage renders correctly across different screen sizes.

### Preconditions
- Browser with responsive design tools (DevTools device toolbar)

### Test Steps

**Test 10A: Mobile Portrait (375px — iPhone SE)**
1. Set viewport to 375px wide
2. Verify:
   - [ ] No horizontal scrollbar
   - [ ] Product cards stack vertically (1 column)
   - [ ] Header/nav is accessible (may collapse to hamburger or stack)
   - [ ] Search input and dropdown are full-width and usable
   - [ ] All text is readable (no truncation issues)

**Test 10B: Mobile Landscape (667px × 375px)**
1. Rotate to landscape
2. Verify product grid adapts appropriately (2 columns may fit)

**Test 10C: Tablet (768px)**
1. Set viewport to 768px wide
2. Verify:
   - [ ] Product cards display in 2-column grid
   - [ ] Header nav is fully visible
   - [ ] No overflow or clipping

**Test 10D: Desktop (1280px)**
1. Set viewport to 1280px wide
2. Verify:
   - [ ] Product cards display in 3+ column grid
   - [ ] All header navigation links visible
   - [ ] Adequate whitespace between cards
   - [ ] No overlapping elements

**Test 10E: Large Desktop (1920px)**
1. Set viewport to 1920px wide
2. Verify layout remains centred and readable

### Post-conditions
- Viewport reset to default

---

## TC-HP-011: Homepage — Product Images

**Priority:** P2 (Medium)
**Type:** Functional
**Module:** Homepage — Product Images
**Estimated Time:** 3 minutes

### Objective
Verify all product images load correctly and lazy loading is implemented.

### Preconditions
- Homepage loaded with DevTools open

### Test Steps

**Test 11A: Images Load Successfully**
1. In DevTools → Network tab, filter by images (or media)
2. Refresh the page
   **Expected:** All 18 product images return HTTP 200 (no 404)

**Test 11B: Lazy Loading**
1. Check the `img` elements in the DOM or Network tab
   **Expected:** Images have `loading="lazy"` attribute

**Test 11C: Broken Images**
1. Scroll through all product cards
   **Expected:** No broken image icons (no generic placeholder broken-image icon)

**Test 11D: Image Aspect Ratios**
1. Verify product images have consistent aspect ratios
   **Expected:** Images are uniform in size/shape (no stretched or squashed images)

### Post-conditions
- No state modified

---

## TC-HP-012: Homepage — Debounce Behaviour

**Priority:** P1 (High)
**Type:** Functional
**Module:** Homepage — Search Debounce
**Estimated Time:** 2 minutes

### Objective
Verify the search input uses debounce to avoid excessive API calls.

### Preconditions
- Homepage loaded with DevTools → Network tab open

### Test Steps

1. Clear the Network tab
2. In the search input, type `"be"` (do not wait)
3. Quickly change to `"beef"` (total time < 200ms)
4. Count API requests fired
   **Expected:** Only 1 API request for `"beef"` (debounce delays the request until typing stops)
5. Clear and type `"av"` → wait 200ms → type `"avo"` → wait 200ms → type `"avoc"` → wait 200ms → type `"avocado"`
   **Expected:** Only 1 API request fires for the final value `"avocado"`

### Post-conditions
- Search input cleared

---

## TC-HP-013: Homepage — Footer Rendering

**Priority:** P2 (Medium)
**Type:** UI/Visual
**Module:** Homepage — Footer
**Estimated Time:** 1 minute

### Objective
Verify the footer renders correctly and is not cut off.

### Preconditions
- Homepage loaded

### Test Steps

1. Scroll to the bottom of the homepage
   **Expected:** Footer is visible and not overlapping other content
2. Verify footer text:
   **Expected:** "© 2026 Bidshop demo – built for the Bidfood SDET technical test."
3. Verify the footer is at the bottom of the page (not mid-page)
4. On mobile viewport (375px), verify footer is still readable and not cut off

### Post-conditions
- No state modified

---

## TC-HP-014: Homepage — Error State (API Failure)

**Priority:** P1 (High)
**Type:** Functional
**Module:** Homepage — Error Handling
**Estimated Time:** 2 minutes

### Objective
Verify the homepage handles backend API failures gracefully.

### Preconditions
- Homepage loaded and working normally

### Test Steps

1. Open DevTools → Network tab
2. Stop the backend server (kill the process on port 4000)
3. Refresh the homepage
   **Expected:** Error message displayed to user (e.g., "Failed to load products" or similar)
   **Expected:** Page does not crash or show a blank white screen
   **Expected:** No JavaScript errors in the console (Error level)
4. Restart the backend server
5. Refresh the page
   **Expected:** Page loads normally with all 18 products

### Post-conditions
- Backend running, homepage loads normally

---

## Smoke Test Suite — Homepage

Run these first on every build. Total time: ~10 minutes.

| ID | Test | Priority | Time |
|----|------|----------|------|
| TC-HP-001 | Homepage — Full Catalogue Load | P0 | 3 min |
| TC-HP-003 | Search by Product Name | P0 | 2 min |
| TC-HP-005 | Category Filter | P0 | 2 min |
| TC-HP-007 | Product Card CTA (Guest) | P0 | 1 min |
| TC-HP-008 | Navigation Links | P0 | 1 min |
| TC-HP-014 | Error State (API Failure) | P1 | 1 min |

---

## Test Execution Tracker

### How to Use

For each test run, copy this template and fill in results:

```markdown
# Homepage Test Run: [Date / Version]

**Date:** YYYY-MM-DD
**Tester:** [Name]
**Environment:** http://localhost:5173
**Build:** [frontend commit hash]

## Summary
| Priority | Total | Pass | Fail | Blocked |
|----------|-------|------|------|---------|
| P0       | 6     |      |      |         |
| P1       | 5     |      |      |         |
| P2       | 3     |      |      |         |
| **Total**| **14**|      |      |         |
| Pass Rate|       |      |      |         |

## Results
| Test ID | Result | Notes |
|---------|--------|-------|
| TC-HP-001 | PASS / FAIL | |
| TC-HP-002 | PASS / FAIL | |
| TC-HP-003 | PASS / FAIL | |
| TC-HP-004 | PASS / FAIL | |
| TC-HP-005 | PASS / FAIL | |
| TC-HP-006 | PASS / FAIL | |
| TC-HP-007 | PASS / FAIL | |
| TC-HP-008 | PASS / FAIL | |
| TC-HP-009 | PASS / FAIL | |
| TC-HP-010 | PASS / FAIL | |
| TC-HP-011 | PASS / FAIL | |
| TC-HP-012 | PASS / FAIL | |
| TC-HP-013 | PASS / FAIL | |
| TC-HP-014 | PASS / FAIL | |

## Failures
| Test ID | Bug ID | Severity | Description |
|---------|--------|----------|-------------|
| TC-HP-XXX | BUG-XXX | P0/P1 | |

## Risks
-

## Recommendation
- [ ] APPROVED
- [ ] BLOCKED

## Next Steps
-
```
