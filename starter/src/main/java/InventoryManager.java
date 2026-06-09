import java.util.*;

/**
 * In-memory inventory manager.
 * Implement every method below — read the Javadoc for expected behavior.
 */
public class InventoryManager {

    // TODO: choose your backing data structure(s)

    /** Add a product. Return true if added, false if duplicate SKU. Throws on null. */
    public boolean addProduct(Product product) {
        // TODO
        return false;
    }

    /** Remove by SKU. Return true if found and removed. */
    public boolean removeProduct(String sku) {
        // TODO
        return false;
    }

    /** Get by SKU, or null if not found. */
    public Product getProduct(String sku) {
        // TODO
        return null;
    }

    /** All products sorted by name. */
    public List<Product> getAllProducts() {
        // TODO
        return List.of();
    }

    /** Case-insensitive name substring search, sorted by name. */
    public List<Product> searchByName(String query) {
        // TODO
        return List.of();
    }

    /** Exact category match, sorted by name. */
    public List<Product> filterByCategory(String category) {
        // TODO
        return List.of();
    }

    /** Price in [min, max] inclusive, sorted by price ascending. */
    public List<Product> filterByPriceRange(double min, double max) {
        // TODO
        return List.of();
    }

    /** Sum of (price * quantity) for all products. */
    public double totalInventoryValue() {
        // TODO
        return 0;
    }

    /** Product with the highest price. Null if empty. */
    public Product mostExpensiveProduct() {
        // TODO
        return null;
    }

    /** Map of category to product count. */
    public Map<String, Integer> categorySummary() {
        // TODO
        return Map.of();
    }
}
