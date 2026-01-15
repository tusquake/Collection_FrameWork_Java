# CopyOnWriteArrayList

## What is it?

`CopyOnWriteArrayList` is a thread-safe variant of `ArrayList` where all write operations (add, set, remove) create a fresh copy of the underlying array.

## Key Concept

- **Reads**: Fast and lock-free
- **Writes**: Create a complete copy of the array (slower)

## When to Use

✅ **Good for:**
- More reads than writes (read-heavy scenarios)
- Small to medium-sized lists
- Iterator consistency is important
- Listener lists, observer patterns

❌ **Avoid when:**
- Frequent writes/modifications
- Large lists (memory overhead)
- Performance-critical write operations

## Basic Example

```java
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteExample {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        
        // Thread 1: Adding elements
        Thread writer = new Thread(() -> {
            list.add("Item 1");
            list.add("Item 2");
            list.add("Item 3");
        });
        
        // Thread 2: Reading elements
        Thread reader = new Thread(() -> {
            for (String item : list) {
                System.out.println(item);
            }
        });
        
        writer.start();
        reader.start();
    }
}
```

## Key Features

### 1. Thread-Safe
No need for external synchronization when multiple threads access it.

### 2. Iterator Never Throws ConcurrentModificationException
```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
list.add("A");
list.add("B");

for (String item : list) {
    list.add("C"); // Safe! No exception thrown
    System.out.println(item); // Only prints A, B
}
```

### 3. Snapshot Iterators
Iterators operate on a snapshot of the list at the time the iterator was created.

## Common Operations

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

// Add
list.add("element");

// Get
String item = list.get(0);

// Remove
list.remove("element");

// Size
int size = list.size();

// Clear
list.clear();

// Iterate (safe even if list is modified)
for (String s : list) {
    System.out.println(s);
}
```

## Real-World Example: Event Listeners

```java
public class EventManager {
    private CopyOnWriteArrayList<EventListener> listeners = 
        new CopyOnWriteArrayList<>();
    
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }
    
    public void notifyListeners(String event) {
        // Safe to iterate even if listeners are added/removed
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
```

## CopyOnWriteArrayList vs ArrayList

| Feature | ArrayList | CopyOnWriteArrayList |
|---------|-----------|---------------------|
| Thread-safe | ❌ No | ✅ Yes |
| Write performance | Fast | Slow (copies array) |
| Read performance | Fast | Fast |
| Memory usage | Low | Higher (copy overhead) |
| Iterator | Fail-fast | Snapshot |
| Best for | Single thread | Multiple threads, read-heavy |

## How It Works

1. **Write Operation**: Creates a new copy of the entire array
   ```
   Original: [A, B, C]
   Add "D" → New Array: [A, B, C, D]
   Old array is replaced with new array
   ```

2. **Read Operation**: Reads from current array (no locking needed)

3. **Iterator**: Uses the array snapshot from when iterator was created

## Important Notes

⚠️ **Write operations are expensive** - Every add/remove copies the entire array

⚠️ **Memory overhead** - Multiple copies may exist temporarily

⚠️ **Iterators don't reflect updates** - They see the list as it was when created

✅ **No ConcurrentModificationException** - Safe to modify during iteration

✅ **Perfect for event listeners** - Rarely modified, frequently read

## Complete Example

```java
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationSystem {
    private CopyOnWriteArrayList<String> subscribers = new CopyOnWriteArrayList<>();
    
    public static void main(String[] args) throws InterruptedException {
        NotificationSystem system = new NotificationSystem();
        
        // Thread 1: Adding subscribers
        Thread adder = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                system.subscribers.add("User" + i);
                System.out.println("Added: User" + i);
            }
        });
        
        // Thread 2: Sending notifications
        Thread notifier = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                system.sendNotification("Alert " + i);
                try { Thread.sleep(100); } catch (Exception e) {}
            }
        });
        
        adder.start();
        notifier.start();
        
        adder.join();
        notifier.join();
    }
    
    public void sendNotification(String message) {
        System.out.println("\nSending: " + message);
        for (String subscriber : subscribers) {
            System.out.println("  → " + subscriber + " notified");
        }
    }
}
```

## Quick Reference

```java
// Create
CopyOnWriteArrayList<T> list = new CopyOnWriteArrayList<>();

// Add
list.add(element);
list.addAll(collection);

// Remove
list.remove(element);
list.clear();

// Read
T item = list.get(index);
int size = list.size();
boolean contains = list.contains(element);

// Iterate (thread-safe)
for (T item : list) {
    // Safe even if list is modified
}
```

## Summary

- **Copy-on-write**: Creates new array copy on every modification
- **Thread-safe**: No external synchronization needed
- **Best for**: Read-heavy, write-rare scenarios
- **Perfect for**: Event listeners, observers, notifications
- **Trade-off**: Memory and write performance for thread-safety and read speed