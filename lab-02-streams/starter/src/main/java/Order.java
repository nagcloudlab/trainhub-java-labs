import java.util.List;

public record Order(String id, String customerEmail, List<OrderItem> items, String status) {
    public double total() {
        return items.stream().mapToDouble(i -> i.quantity() * i.unitPrice()).sum();
    }
}
