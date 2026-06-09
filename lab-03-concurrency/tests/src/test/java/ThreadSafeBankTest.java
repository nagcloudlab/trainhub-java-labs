import org.junit.jupiter.api.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import static org.junit.jupiter.api.Assertions.*;

class ThreadSafeBankTest {

    private ThreadSafeBank bank;

    @BeforeEach void setUp() {
        bank = new ThreadSafeBank();
        bank.createAccount("A", 1000);
        bank.createAccount("B", 500);
    }

    @Nested class BasicOperations {
        @Test void createAndGetBalance() { assertEquals(1000, bank.getBalance("A"), 0.01); }
        @Test void deposit() { bank.deposit("A", 200); assertEquals(1200, bank.getBalance("A"), 0.01); }
        @Test void withdrawSuccess() { assertTrue(bank.withdraw("A", 300)); assertEquals(700, bank.getBalance("A"), 0.01); }
        @Test void withdrawInsufficient() { assertFalse(bank.withdraw("A", 2000)); assertEquals(1000, bank.getBalance("A"), 0.01); }
        @Test void unknownAccountThrows() { assertThrows(IllegalArgumentException.class, () -> bank.getBalance("Z")); }
        @Test void createAccountIdempotent() { bank.createAccount("A", 9999); assertEquals(1000, bank.getBalance("A"), 0.01); }
        @Test void transferBasic() { assertTrue(bank.transfer("A", "B", 200)); assertEquals(800, bank.getBalance("A"), 0.01); assertEquals(700, bank.getBalance("B"), 0.01); }
    }

    @Nested class ThreadSafety {
        @Test void concurrentDeposits() throws Exception {
            int threads = 100;
            var latch = new CountDownLatch(threads);
            var exec = Executors.newFixedThreadPool(threads);
            for (int i = 0; i < threads; i++) {
                exec.submit(() -> { bank.deposit("A", 10); latch.countDown(); });
            }
            latch.await(5, TimeUnit.SECONDS);
            exec.shutdown();
            assertEquals(2000, bank.getBalance("A"), 0.01);
        }

        @Test void concurrentTransfersNoMoneyLost() throws Exception {
            bank.createAccount("C", 1000);
            int rounds = 50;
            var latch = new CountDownLatch(rounds * 2);
            var exec = Executors.newFixedThreadPool(10);
            for (int i = 0; i < rounds; i++) {
                exec.submit(() -> { bank.transfer("A", "B", 10); latch.countDown(); });
                exec.submit(() -> { bank.transfer("B", "A", 10); latch.countDown(); });
            }
            latch.await(5, TimeUnit.SECONDS);
            exec.shutdown();
            double total = bank.getBalance("A") + bank.getBalance("B") + bank.getBalance("C");
            assertEquals(2500, total, 0.01);
        }
    }

    @Nested class AsyncTransfers {
        @Test void transferAsync() throws Exception {
            var future = bank.transferAsync("A", "B", 100);
            assertTrue(future.get(2, TimeUnit.SECONDS));
            assertEquals(900, bank.getBalance("A"), 0.01);
        }

        @Test void batchTransfer() {
            bank.createAccount("C", 300);
            var transfers = List.of(
                new Transfer("A", "B", 100),
                new Transfer("B", "C", 50),
                new Transfer("C", "A", 25)
            );
            int successes = bank.batchTransfer(transfers);
            assertEquals(3, successes);
        }

        @Test void batchTransferPartialFailure() {
            var transfers = List.of(
                new Transfer("A", "B", 100),
                new Transfer("A", "B", 99999)
            );
            int successes = bank.batchTransfer(transfers);
            assertTrue(successes >= 1 && successes <= 2);
        }
    }
}
