import java.util.function.Consumer;

/** Simple observer-pattern event bus. */
public class EventBus {

    /** Register a listener for an event type. */
    public void subscribe(String eventType, Consumer<String> listener) {
        // TODO
    }

    /** Notify all listeners for the given event type. */
    public void publish(String eventType, String data) {
        // TODO
    }

    /** Remove a listener. */
    public void unsubscribe(String eventType, Consumer<String> listener) {
        // TODO
    }
}
