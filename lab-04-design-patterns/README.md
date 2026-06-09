# Design Patterns — Notification System

Build a notification system using Strategy, Observer, and Builder patterns.

## Part 1: Strategy Pattern (35 pts)

Implement `NotificationChannel` interface with three strategies:
- `EmailChannel` — formats as `"[EMAIL] To: {recipient} | {message}"`
- `SmsChannel` — formats as `"[SMS] {recipient}: {message}"` (truncate message to 160 chars)
- `SlackChannel` — formats as `"[SLACK] #{channel} @{recipient}: {message}"`

The `NotificationSender` accepts a channel strategy and sends through it.

## Part 2: Observer Pattern (35 pts)

Implement `EventBus`:
- `subscribe(eventType, listener)` — register a listener for an event type
- `publish(eventType, data)` — notify all listeners for that event type
- `unsubscribe(eventType, listener)` — remove a listener
- Listeners receive the event data string

## Part 3: Builder Pattern (30 pts)

Implement `Notification.builder()`:
- Required: `recipient`, `message`
- Optional: `channel` (default: EMAIL), `priority` (default: NORMAL), `metadata` (key-value pairs)
- `build()` throws if required fields are missing

## Running Tests

```bash
mvn test
```
