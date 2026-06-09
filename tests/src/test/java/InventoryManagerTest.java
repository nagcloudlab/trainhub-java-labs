import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {

    private InventoryManager mgr;
    private final Product laptop = new Product("SKU-001", "Laptop Pro", "Electronics", 1299.99, 10);
    private final Product mouse = new Product("SKU-002", "Wireless Mouse", "Electronics", 29.99, 200);
    private final Product desk = new Product("SKU-003", "Standing Desk", "Furniture", 499.00, 25);
    private final Product chair = new Product("SKU-004", "Ergonomic Chair", "Furniture", 349.00, 40);
    private final Product cable = new Product("SKU-005", "USB-C Cable", "Electronics", 12.99, 500);

    @BeforeEach void setUp() { mgr = new InventoryManager(); }

    @Nested class ProductCrud {
        @Test void addProduct_success() { assertTrue(mgr.addProduct(laptop)); assertEquals(laptop, mgr.getProduct("SKU-001")); }
        @Test void addProduct_rejectsDuplicate() { mgr.addProduct(laptop); assertFalse(mgr.addProduct(laptop)); }
        @Test void addProduct_nullThrows() { assertThrows(IllegalArgumentException.class, () -> mgr.addProduct(null)); }
        @Test void removeProduct_success() { mgr.addProduct(laptop); assertTrue(mgr.removeProduct("SKU-001")); assertNull(mgr.getProduct("SKU-001")); }
        @Test void removeProduct_notFound() { assertFalse(mgr.removeProduct("NOPE")); }
        @Test void getAllProducts_sortedByName() { mgr.addProduct(desk); mgr.addProduct(laptop); mgr.addProduct(cable); var all = mgr.getAllProducts(); assertEquals(3, all.size()); assertEquals("Laptop Pro", all.get(0).name()); }
        @Test void getAllProducts_emptyInventory() { assertTrue(mgr.getAllProducts().isEmpty()); }
    }

    @Nested class SearchFilter {
        @BeforeEach void populate() { mgr.addProduct(laptop); mgr.addProduct(mouse); mgr.addProduct(desk); mgr.addProduct(chair); mgr.addProduct(cable); }
        @Test void searchByName_caseInsensitive() { var r = mgr.searchByName("pro"); assertEquals(1, r.size()); assertEquals("Laptop Pro", r.get(0).name()); }
        @Test void searchByName_noMatch() { assertTrue(mgr.searchByName("xyz").isEmpty()); }
        @Test void filterByCategory_electronics() { assertEquals(3, mgr.filterByCategory("Electronics").size()); }
        @Test void filterByCategory_noMatch() { assertTrue(mgr.filterByCategory("Food").isEmpty()); }
        @Test void filterByPriceRange_inclusive() { var r = mgr.filterByPriceRange(29.99, 499.00); assertEquals(3, r.size()); }
        @Test void filterByPriceRange_noMatch() { assertTrue(mgr.filterByPriceRange(5000, 9000).isEmpty()); }
    }

    @Nested class Analytics {
        @BeforeEach void populate() { mgr.addProduct(laptop); mgr.addProduct(mouse); mgr.addProduct(desk); mgr.addProduct(cable); }
        @Test void totalInventoryValue() { assertEquals(12999.9 + 5998.0 + 12475.0 + 6495.0, mgr.totalInventoryValue(), 0.01); }
        @Test void mostExpensiveProduct() { assertEquals(laptop, mgr.mostExpensiveProduct()); }
        @Test void categorySummary() { var s = mgr.categorySummary(); assertEquals(3, s.get("Electronics")); assertEquals(1, s.get("Furniture")); }
    }

    @Nested class EdgeCases {
        @Test void totalInventoryValue_empty() { assertEquals(0, mgr.totalInventoryValue(), 0.001); }
        @Test void mostExpensiveProduct_empty() { assertNull(mgr.mostExpensiveProduct()); }
        @Test void searchByName_nullReturnsEmpty() { assertTrue(mgr.searchByName(null).isEmpty()); }
        @Test void removeProduct_nullReturnsFalse() { assertFalse(mgr.removeProduct(null)); }
    }
}
