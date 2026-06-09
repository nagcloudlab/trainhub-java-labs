# Java HTTP — REST Client Wrapper

Build a type-safe REST client wrapper using Java's `HttpClient` (no external libraries).

## Your Task

Implement `SimpleRestClient.java` — a reusable HTTP client with JSON support, error handling, retries, and timeouts.

### Part 1: GET Requests (30 pts)
- `get(url)` → returns `ApiResponse` with status, body, headers
- `getJson(url, Class<T>)` → deserializes JSON body to given type
- Handle non-2xx responses with `ApiException`

### Part 2: POST and Error Handling (35 pts)
- `post(url, body)` → sends JSON body, returns `ApiResponse`
- `ApiException` carries status code and response body
- Null/empty URL throws `IllegalArgumentException`

### Part 3: Retry and Timeout (35 pts)
- Constructor accepts `maxRetries` (default 0) and `timeoutSeconds` (default 30)
- Retry on 5xx responses and `IOException`, up to `maxRetries`
- Timeout applied per-request

## Data Model

```java
record ApiResponse(int statusCode, String body, Map<String, String> headers)
```

## Note

Tests use a mock HTTP handler — no real HTTP calls needed. Focus on the logic, not the network.

## Running Tests

```bash
mvn test
```
