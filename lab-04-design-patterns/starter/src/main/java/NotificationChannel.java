/** Strategy interface for notification delivery. */
public interface NotificationChannel {
    String send(String recipient, String message);
}
