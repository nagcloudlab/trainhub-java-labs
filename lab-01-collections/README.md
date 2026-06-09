# Java Collections — Inventory Manager

Build an in-memory inventory manager using Java Collections.

## Your Task

Implement all methods in `InventoryManager.java`. Each method has a Javadoc describing the expected behavior.

### Part 1: Product CRUD (30 pts)
- `addProduct(Product)` — add a product, reject duplicates by SKU
- `removeProduct(String sku)` — remove by SKU, return true if found
- `getProduct(String sku)` — look up by SKU
- `getAllProducts()` — return all products sorted by name

### Part 2: Search and Filter (30 pts)
- `searchByName(String query)` — case-insensitive substring match
- `filterByCategory(String category)` — exact category match
- `filterByPriceRange(double min, double max)` — inclusive range

### Part 3: Inventory Analytics (25 pts)
- `totalInventoryValue()` — sum of (price * quantity) for all products
- `mostExpensiveProduct()` — product with highest price
- `categorySummary()` — map of category to product count

### Part 4: Edge Cases (15 pts)
- Null/empty inputs handled gracefully
- Correct behavior on empty inventory

## Running Tests

```bash
mvn test
```

## Data Model

```java
record Product(String sku, String name, String category, double price, int quantity)
```
