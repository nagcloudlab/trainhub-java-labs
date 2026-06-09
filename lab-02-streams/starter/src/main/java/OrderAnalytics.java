import java.util.*;

/**
 * Analyze orders using only Stream API — no manual loops allowed.
 */
public class OrderAnalytics {

    private final List<Order> orders;

    public OrderAnalytics(List<Order> orders) {
        this.orders = orders;
    }

    /** List of order totals. */
    public List<Double> orderTotals() {
        // TODO: use streams
        return List.of();
    }

    /** Unique customer emails, sorted alphabetically. */
    public List<String> customerEmails() {
        // TODO
        return List.of();
    }

    /** Orders where total > threshold. */
    public List<Order> highValueOrders(double threshold) {
        // TODO
        return List.of();
    }

    /** Sum of all order totals. */
    public double totalRevenue() {
        // TODO
        return 0;
    }

    /** Average order total. Return 0 if no orders. */
    public double averageOrderValue() {
        // TODO
        return 0;
    }

    /** Order with the highest total. Null if empty. */
    public Order largestOrder() {
        // TODO
        return null;
    }

    /** Map of customer email to total amount spent. */
    public Map<String, Double> revenueByCustomer() {
        // TODO
        return Map.of();
    }

    /** Map of order status to count. */
    public Map<String, Long> orderCountByStatus() {
        // TODO
        return Map.of();
    }

    /** Top N product names by total revenue, descending. */
    public List<String> topProductsByRevenue(int n) {
        // TODO
        return List.of();
    }
}
