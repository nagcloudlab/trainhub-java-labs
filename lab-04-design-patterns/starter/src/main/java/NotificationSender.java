/** Sends notifications through a pluggable channel strategy. */
public class NotificationSender {
    private final NotificationChannel channel;
    public NotificationSender(NotificationChannel channel) { this.channel = channel; }

    public String send(String recipient, String message) {
        // TODO: delegate to channel
        return "";
    }
}
