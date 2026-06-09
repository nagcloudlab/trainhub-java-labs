import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.*;
import static org.junit.jupiter.api.Assertions.*;

class SimpleRestClientTest {

    @Nested class GetRequests {
        @Test void getSuccess() {
            var client = new SimpleRestClient((method, url, body, headers) ->
                new ApiResponse(200, "{\"ok\":true}", Map.of("Content-Type", "application/json")));
            var resp = client.get("https://api.test/health");
            assertEquals(200, resp.statusCode());
            assertTrue(resp.isSuccess());
        }

        @Test void getJsonParses() {
            var client = new SimpleRestClient((method, url, body, headers) ->
                new ApiResponse(200, "42", Map.of()));
            int result = client.getJson("https://api.test/count", Integer::parseInt);
            assertEquals(42, result);
        }

        @Test void getNon2xxThrowsApiException() {
            var client = new SimpleRestClient((method, url, body, headers) ->
                new ApiResponse(404, "Not Found", Map.of()));
            var ex = assertThrows(ApiException.class, () -> client.get("https://api.test/missing"));
            assertEquals(404, ex.getStatusCode());
        }

        @Test void getNullUrlThrows() {
            var client = new SimpleRestClient((m, u, b, h) -> new ApiResponse(200, "", Map.of()));
            assertThrows(IllegalArgumentException.class, () -> client.get(null));
        }

        @Test void getEmptyUrlThrows() {
            var client = new SimpleRestClient((m, u, b, h) -> new ApiResponse(200, "", Map.of()));
            assertThrows(IllegalArgumentException.class, () -> client.get(""));
        }
    }

    @Nested class PostAndErrorHandling {
        @Test void postSuccess() {
            var client = new SimpleRestClient((method, url, body, headers) -> {
                assertEquals("POST", method);
                return new ApiResponse(201, "{\"id\":1}", Map.of());
            });
            var resp = client.post("https://api.test/items", "{\"name\":\"widget\"}");
            assertEquals(201, resp.statusCode());
        }

        @Test void postNon2xxThrows() {
            var client = new SimpleRestClient((m, u, b, h) -> new ApiResponse(422, "Validation error", Map.of()));
            var ex = assertThrows(ApiException.class, () -> client.post("https://api.test/items", "{}"));
            assertEquals(422, ex.getStatusCode());
            assertEquals("Validation error", ex.getResponseBody());
        }

        @Test void postNullUrlThrows() {
            var client = new SimpleRestClient((m, u, b, h) -> new ApiResponse(200, "", Map.of()));
            assertThrows(IllegalArgumentException.class, () -> client.post(null, "{}"));
        }
    }

    @Nested class RetryAndTimeout {
        @Test void retriesOn5xx() {
            var attempts = new AtomicInteger(0);
            var client = new SimpleRestClient((method, url, body, headers) -> {
                if (attempts.incrementAndGet() < 3) return new ApiResponse(503, "Retry", Map.of());
                return new ApiResponse(200, "OK", Map.of());
            }, 3, 30);
            var resp = client.get("https://api.test/flaky");
            assertEquals(200, resp.statusCode());
            assertEquals(3, attempts.get());
        }

        @Test void retriesOnIOException() {
            var attempts = new AtomicInteger(0);
            var client = new SimpleRestClient((method, url, body, headers) -> {
                if (attempts.incrementAndGet() < 2) throw new IOException("Connection reset");
                return new ApiResponse(200, "OK", Map.of());
            }, 2, 30);
            var resp = client.get("https://api.test/unstable");
            assertEquals(200, resp.statusCode());
        }

        @Test void exhaustsRetriesThrows() {
            var client = new SimpleRestClient((m, u, b, h) -> new ApiResponse(500, "Error", Map.of()), 2, 30);
            assertThrows(ApiException.class, () -> client.get("https://api.test/down"));
        }

        @Test void noRetryOn4xx() {
            var attempts = new AtomicInteger(0);
            var client = new SimpleRestClient((m, u, b, h) -> {
                attempts.incrementAndGet();
                return new ApiResponse(400, "Bad", Map.of());
            }, 3, 30);
            assertThrows(ApiException.class, () -> client.get("https://api.test/bad"));
            assertEquals(1, attempts.get());
        }
    }
}
