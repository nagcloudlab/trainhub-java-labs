import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderAnalyticsTest {

    private OrderAnalytics analytics;

    @BeforeEach
    void setUp() {
        var orders = List.of(
            new Order("O1", "alice@test.com", List.of(
                new OrderItem("Laptop", 1, 1200.00),
                new OrderItem("Mouse", 2, 25.00)
            ), "COMPLETED"),
            new Order("O2", "bob@test.com", List.of(
                new OrderItem("Keyboard", 1, 75.00)
            ), "COMPLETED"),
            new Order("O3", "alice@test.com", List.of(
                new OrderItem("Monitor", 1, 450.00),
                new OrderItem("Mouse", 1, 25.00)
            ), "PENDING"),
            new Order("O4", "carol@test.com", List.of(
                new OrderItem("Laptop", 1, 1200.00)
            ), "CANCELLED")
        );
        analytics = new OrderAnalytics(orders);
    }

    @Nested class BasicTransformations {
        @Test void orderTotals() {
            var totals = analytics.orderTotals();
            assertEquals(4, totals.size());
            assertEquals(1250.00, totals.get(0), 0.01);
            assertEquals(75.00, totals.get(1), 0.01);
        }

        @Test void customerEmails() {
            var emails = analytics.customerEmails();
            assertEquals(List.of("alice@test.com", "bob@test.com", "carol@test.com"), emails);
        }

        @Test void highValueOrders() {
            var high = analytics.highValueOrders(500);
            assertEquals(2, high.size());
        }
    }

    @Nested class Aggregations {
        @Test void totalRevenue() {
            assertEquals(1250 + 75 + 475 + 1200, analytics.totalRevenue(), 0.01);
        }

        @Test void averageOrderValue() {
            assertEquals(750.0, analytics.averageOrderValue(), 0.01);
        }

        @Test void largestOrder() {
            assertEquals("O1", analytics.largestOrder().id());
        }

        @Test void emptyAnalytics() {
            var empty = new OrderAnalytics(List.of());
            assertEquals(0, empty.totalRevenue(), 0.01);
            assertEquals(0, empty.averageOrderValue(), 0.01);
            assertNull(empty.largestOrder());
        }
    }

    @Nested class AdvancedCollectors {
        @Test void revenueByCustomer() {
            var rev = analytics.revenueByCustomer();
            assertEquals(1725.00, rev.get("alice@test.com"), 0.01);
            assertEquals(75.00, rev.get("bob@test.com"), 0.01);
        }

        @Test void orderCountByStatus() {
            var counts = analytics.orderCountByStatus();
            assertEquals(2L, counts.get("COMPLETED"));
            assertEquals(1L, counts.get("PENDING"));
            assertEquals(1L, counts.get("CANCELLED"));
        }

        @Test void topProductsByRevenue() {
            var top = analytics.topProductsByRevenue(2);
            assertEquals(2, top.size());
            assertEquals("Laptop", top.get(0));
        }
    }
}
