# Java Streams — Order Analytics

Use the Java Stream API to analyze e-commerce order data.

## Your Task

Implement all methods in `OrderAnalytics.java` using **only Stream API operations** — no manual loops.

### Part 1: Basic Transformations (30 pts)
- `orderTotals()` — list of order totals (quantity * unitPrice per item)
- `customerEmails()` — unique sorted email addresses
- `highValueOrders(double threshold)` — orders with total > threshold

### Part 2: Aggregations (35 pts)
- `totalRevenue()` — sum of all order totals
- `averageOrderValue()` — mean order total
- `largestOrder()` — order with the highest total

### Part 3: Advanced Collectors (35 pts)
- `revenueByCustomer()` — map of customer email to total spent
- `orderCountByStatus()` — map of status to count
- `topProductsByRevenue(int n)` — top N product names by total revenue

## Data Model

```java
record Order(String id, String customerEmail, List<OrderItem> items, String status)
record OrderItem(String productName, int quantity, double unitPrice)
```

## Running Tests

```bash
mvn test
```
