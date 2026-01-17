# LinkedBlockingQueue

## What is it?

Thread-safe, optionally bounded blocking queue. **Blocks** when queue is full (put) or empty (take).

**Core Concept**: Producer waits when queue is full, consumer waits when empty. Perfect for producer-consumer patterns.

## Key Features

- **Blocking**: Threads wait instead of failing
- **Thread-safe**: Multiple producers/consumers
- **Optionally bounded**: Set max capacity or unbounded
- **Two locks**: Separate locks for put/take (better concurrency)
- **FIFO ordering**: First In, First Out

## Basic Example

```java
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueExample {
    public static void main(String[] args) {
        // Bounded queue - capacity of 3
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);
        
        // Producer
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Producing: " + i);
                    queue.put(i);  // BLOCKS if queue is FULL
                    System.out.println("Produced: " + i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        // Consumer (slow)
        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(1000);  // Slow consumer
                    int value = queue.take();  // BLOCKS if queue is EMPTY
                    System.out.println("Consumed: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        new Thread(producer, "Producer").start();
        new Thread(consumer, "Consumer").start();
    }
}
```

## Output Flow

```
Producing: 1
Produced: 1      → Queue: [1]
Producing: 2
Produced: 2      → Queue: [1, 2]
Producing: 3
Produced: 3      → Queue: [1, 2, 3] (FULL - capacity reached)
Producing: 4     → Producer BLOCKS (waiting for space)

(Consumer takes 1 second to wake up)
Consumed: 1      → Queue: [2, 3] (space available)
Produced: 4      → Producer UNBLOCKS, Queue: [2, 3, 4]
Producing: 5     → Producer BLOCKS again

Consumed: 2      → Queue: [3, 4]
Produced: 5      → Queue: [3, 4, 5]

Consumed: 3      → Queue: [4, 5]
Consumed: 4      → Queue: [5]
Consumed: 5      → Queue: []
```

## Common Operations

```java
BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

// Blocking operations (wait if needed)
queue.put("item");           // Blocks if full
String item = queue.take();  // Blocks if empty

// Non-blocking with timeout
queue.offer("item", 5, TimeUnit.SECONDS);  // Wait max 5 sec
String item = queue.poll(5, TimeUnit.SECONDS);

// Non-blocking immediate
boolean success = queue.offer("item");  // false if full
String item = queue.poll();             // null if empty

// Other
String head = queue.peek();             // View without removing
int size = queue.size();
int space = queue.remainingCapacity();
```

## Method Comparison

| Operation | Throws Exception | Returns Special | Blocks | Times Out |
|-----------|------------------|-----------------|--------|-----------|
| Insert | add(e) | offer(e) | **put(e)** | offer(e, time) |
| Remove | remove() | poll() | **take()** | poll(time) |
| Examine | element() | peek() | N/A | N/A |

## Real-World Example: Order Processing

```java
public class RestaurantQueue {
    private BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>(50);
    
    // Cashier receives orders (Producer)
    public void receiveOrder(Order order) {
        try {
            orderQueue.put(order);  // Wait if queue full
            System.out.println("Order queued: " + order.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Chef processes orders (Consumer)
    public void processOrders() {
        while (true) {
            try {
                Order order = orderQueue.take();  // Wait if no orders
                System.out.println("Cooking: " + order.getId());
                Thread.sleep(3000);  // Cook time
                System.out.println("Ready: " + order.getId());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
```

## vs Other Queues

| Queue | Blocking | Bounded | Locks |
|-------|----------|---------|-------|
| LinkedBlockingQueue | Yes | Optional | Two (put/take) |
| ArrayBlockingQueue | Yes | Always | One |
| ConcurrentLinkedQueue | No | No | Lock-free |

## When to Use

**Use when:**
- Producer and consumer run at different speeds
- Need blocking behavior (wait, don't fail)
- Multiple producers/consumers
- Classic producer-consumer pattern

**Avoid when:**
- Don't need blocking (use ConcurrentLinkedQueue)
- Need strict bounded capacity (use ArrayBlockingQueue)
- Single threaded

## Important Notes

**Always handle InterruptedException:**
```java
try {
    queue.put(item);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();  // Restore interrupt
}
```

**Set capacity in production:**
```java
// Bad - unbounded, can cause OutOfMemoryError
BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

// Good - bounded
BlockingQueue<Task> queue = new LinkedBlockingQueue<>(1000);
```

**Use offer/poll for non-critical operations:**
```java
// Must succeed - use blocking
queue.put(criticalData);

// Can skip if full
if (!queue.offer(logMessage)) {
    // Skip or log warning
}
```

## Quick Reference

```java
// Create
BlockingQueue<T> queue = new LinkedBlockingQueue<>(capacity);

// Blocking
queue.put(item);              // Wait if full
T item = queue.take();        // Wait if empty

// Non-blocking
queue.offer(item);            // false if full
T item = queue.poll();        // null if empty

// Timed
queue.offer(item, 5, TimeUnit.SECONDS);
T item = queue.poll(5, TimeUnit.SECONDS);
```

## Summary

- **Blocking queue** - producers/consumers wait automatically
- **Two locks** - put and take can happen concurrently
- **Optionally bounded** - set capacity or leave unbounded
- **Perfect for** - producer-consumer with different speeds
- **Use put/take** - for critical blocking operations
- **Use offer/poll** - for non-critical or timed operations