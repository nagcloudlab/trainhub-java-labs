import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Thread-safe bank. All methods must be safe for concurrent access.
 */
public class ThreadSafeBank {

    /** Create an account with initial balance. Idempotent — ignore if already exists. */
    public void createAccount(String id, double initialBalance) {
        // TODO
    }

    /** Get current balance. Throws IllegalArgumentException if account not found. */
    public double getBalance(String id) {
        // TODO
        throw new IllegalArgumentException("Account not found: " + id);
    }

    /** Deposit amount into account. Amount must be positive. */
    public void deposit(String id, double amount) {
        // TODO
    }

    /** Withdraw amount. Return true if successful, false if insufficient funds. */
    public boolean withdraw(String id, double amount) {
        // TODO
        return false;
    }

    /** Transfer between accounts. Return true if successful. Must not deadlock. */
    public boolean transfer(String fromId, String toId, double amount) {
        // TODO
        return false;
    }

    /** Async transfer returning a CompletableFuture. */
    public CompletableFuture<Boolean> transferAsync(String fromId, String toId, double amount) {
        // TODO
        return CompletableFuture.completedFuture(false);
    }

    /** Execute transfers concurrently, return count of successful ones. */
    public int batchTransfer(List<Transfer> transfers) {
        // TODO
        return 0;
    }
}
