import java.util.Map;

/**
 * Abstraction over HTTP transport.
 * Tests inject a mock; your production code would use java.net.http.HttpClient.
 */
public interface HttpHandler {
    ApiResponse execute(String method, String url, String body, Map<String, String> headers) throws Exception;
}
