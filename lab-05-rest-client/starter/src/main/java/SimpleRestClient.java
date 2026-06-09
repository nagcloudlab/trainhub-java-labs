import java.util.Map;

/**
 * A reusable REST client with error handling, retries, and timeout.
 */
public class SimpleRestClient {

    private final HttpHandler handler;
    private final int maxRetries;
    private final int timeoutSeconds;

    public SimpleRestClient(HttpHandler handler) {
        this(handler, 0, 30);
    }

    public SimpleRestClient(HttpHandler handler, int maxRetries, int timeoutSeconds) {
        this.handler = handler;
        this.maxRetries = maxRetries;
        this.timeoutSeconds = timeoutSeconds;
    }

    /** GET request. Throws ApiException on non-2xx. */
    public ApiResponse get(String url) {
        // TODO: validate url, call handler with GET, handle errors
        throw new UnsupportedOperationException("implement me");
    }

    /** GET + deserialize JSON body. Uses simple manual parsing (no Jackson needed). */
    public <T> T getJson(String url, JsonParser<T> parser) {
        // TODO: get(url) then parse body
        throw new UnsupportedOperationException("implement me");
    }

    /** POST with JSON body. Throws ApiException on non-2xx. */
    public ApiResponse post(String url, String jsonBody) {
        // TODO: validate, call handler with POST, handle errors
        throw new UnsupportedOperationException("implement me");
    }

    /** Functional interface for JSON parsing (avoids Jackson dependency). */
    @FunctionalInterface
    public interface JsonParser<T> {
        T parse(String json);
    }
}
