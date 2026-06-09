import java.util.*;

/** Immutable notification built via the Builder pattern. */
public class Notification {

    public enum Priority { LOW, NORMAL, HIGH, URGENT }

    private final String recipient;
    private final String message;
    private final String channel;
    private final Priority priority;
    private final Map<String, String> metadata;

    private Notification(String recipient, String message, String channel, Priority priority, Map<String, String> metadata) {
        this.recipient = recipient;
        this.message = message;
        this.channel = channel;
        this.priority = priority;
        this.metadata = metadata;
    }

    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getChannel() { return channel; }
    public Priority getPriority() { return priority; }
    public Map<String, String> getMetadata() { return metadata; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        // TODO: implement builder fields and methods
        // Required: recipient(String), message(String)
        // Optional: channel(String) default "EMAIL", priority(Priority) default NORMAL, metadata(String key, String value)
        // build() throws IllegalStateException if required fields missing

        public Notification build() {
            // TODO
            throw new UnsupportedOperationException("implement me");
        }
    }
}
