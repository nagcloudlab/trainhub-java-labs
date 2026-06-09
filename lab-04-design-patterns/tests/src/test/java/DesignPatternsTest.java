import org.junit.jupiter.api.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import static org.junit.jupiter.api.Assertions.*;

class DesignPatternsTest {

    @Nested class StrategyPattern {
        @Test void emailChannel() {
            var sender = new NotificationSender(new EmailChannel());
            assertEquals("[EMAIL] To: alice@test.com | Hello!", sender.send("alice@test.com", "Hello!"));
        }

        @Test void smsChannel() {
            var sender = new NotificationSender(new SmsChannel());
            assertEquals("[SMS] +1234: Hi", sender.send("+1234", "Hi"));
        }

        @Test void smsChannelTruncates() {
            var sender = new NotificationSender(new SmsChannel());
            var longMsg = "A".repeat(200);
            var result = sender.send("+1234", longMsg);
            assertTrue(result.contains("A".repeat(160)));
            assertFalse(result.contains("A".repeat(161)));
        }

        @Test void slackChannel() {
            var sender = new NotificationSender(new SlackChannel("general"));
            assertEquals("[SLACK] #general @bob: Update ready", sender.send("bob", "Update ready"));
        }

        @Test void switchStrategy() {
            var email = new NotificationSender(new EmailChannel());
            var sms = new NotificationSender(new SmsChannel());
            assertNotEquals(email.send("x", "hi"), sms.send("x", "hi"));
        }
    }

    @Nested class ObserverPattern {
        @Test void subscribeAndPublish() {
            var bus = new EventBus();
            var received = new AtomicReference<String>();
            bus.subscribe("order.created", received::set);
            bus.publish("order.created", "ORD-001");
            assertEquals("ORD-001", received.get());
        }

        @Test void multipleListeners() {
            var bus = new EventBus();
            var count = new AtomicInteger(0);
            bus.subscribe("click", data -> count.incrementAndGet());
            bus.subscribe("click", data -> count.incrementAndGet());
            bus.publish("click", "btn1");
            assertEquals(2, count.get());
        }

        @Test void unsubscribe() {
            var bus = new EventBus();
            var count = new AtomicInteger(0);
            Consumer<String> listener = data -> count.incrementAndGet();
            bus.subscribe("tick", listener);
            bus.publish("tick", "1");
            bus.unsubscribe("tick", listener);
            bus.publish("tick", "2");
            assertEquals(1, count.get());
        }

        @Test void differentEventTypes() {
            var bus = new EventBus();
            var aCount = new AtomicInteger(0);
            var bCount = new AtomicInteger(0);
            bus.subscribe("a", data -> aCount.incrementAndGet());
            bus.subscribe("b", data -> bCount.incrementAndGet());
            bus.publish("a", "x");
            assertEquals(1, aCount.get());
            assertEquals(0, bCount.get());
        }

        @Test void publishNoListeners() {
            var bus = new EventBus();
            assertDoesNotThrow(() -> bus.publish("ghost", "data"));
        }
    }

    @Nested class BuilderPattern {
        @Test void buildWithRequired() {
            var n = Notification.builder().recipient("alice").message("Hi").build();
            assertEquals("alice", n.getRecipient());
            assertEquals("Hi", n.getMessage());
            assertEquals("EMAIL", n.getChannel());
            assertEquals(Notification.Priority.NORMAL, n.getPriority());
        }

        @Test void buildWithAllFields() {
            var n = Notification.builder()
                .recipient("bob")
                .message("Alert")
                .channel("SMS")
                .priority(Notification.Priority.URGENT)
                .metadata("key", "value")
                .build();
            assertEquals("SMS", n.getChannel());
            assertEquals(Notification.Priority.URGENT, n.getPriority());
            assertEquals("value", n.getMetadata().get("key"));
        }

        @Test void buildMissingRecipientThrows() {
            assertThrows(IllegalStateException.class, () ->
                Notification.builder().message("Hi").build());
        }

        @Test void buildMissingMessageThrows() {
            assertThrows(IllegalStateException.class, () ->
                Notification.builder().recipient("alice").build());
        }

        @Test void metadataIsUnmodifiable() {
            var n = Notification.builder().recipient("a").message("b").metadata("k", "v").build();
            assertThrows(UnsupportedOperationException.class, () -> n.getMetadata().put("x", "y"));
        }
    }
}
