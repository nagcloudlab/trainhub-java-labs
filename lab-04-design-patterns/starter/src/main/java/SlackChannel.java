/** Formats as: [SLACK] #{channel} @{recipient}: {message} */
public class SlackChannel implements NotificationChannel {
    private final String channel;
    public SlackChannel(String channel) { this.channel = channel; }

    @Override
    public String send(String recipient, String message) {
        // TODO
        return "";
    }
}
