# Java Concurrency — Thread-Safe Bank

Build a thread-safe bank account system that handles concurrent deposits, withdrawals, and transfers.

## Your Task

Implement `ThreadSafeBank.java` so that all operations are safe under concurrent access.

### Part 1: Basic Operations (30 pts)
- `createAccount(id, initialBalance)` — create an account
- `getBalance(id)` — return current balance
- `deposit(id, amount)` — add to balance
- `withdraw(id, amount)` — subtract if sufficient funds, return success

### Part 2: Thread Safety (40 pts)
- Concurrent deposits and withdrawals must not lose money
- No deadlocks on transfers between accounts
- Account creation must be idempotent

### Part 3: Async Transfers (30 pts)
- `transferAsync(from, to, amount)` — returns CompletableFuture<Boolean>
- `batchTransfer(List<Transfer>)` — execute multiple transfers concurrently, return count of successes

## Running Tests

```bash
mvn test
```
