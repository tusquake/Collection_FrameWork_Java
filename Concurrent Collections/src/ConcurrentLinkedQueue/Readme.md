# ConcurrentLinkedQueue

## What is it?

A thread-safe, unbounded FIFO (First-In-First-Out) queue implemented using lock-free operations. Part of `java.util.concurrent` package.

**Core Concept**: Instead of using traditional locks (synchronized blocks), it uses **CAS (Compare-And-Swap)** - a hardware-level atomic instruction that allows multiple threads to safely modify the queue without blocking each other.

## How It Works Internally

**Data Structure**: Linked list of nodes
```
Head -> [Node1] -> [Node2] -> [Node3] -> Tail
```

**Lock-Free Algorithm**:
- Each node has a reference to the next node
- When adding/removing, threads use CAS to atomically update references
- If CAS fails (another thread modified it), thread retries in a loop
- No thread ever waits or blocks - just keeps trying until successful

**Why Lock-Free is Better**:
- Traditional locks: Thread acquires lock -> other threads wait (blocked)
- Lock-free CAS: All threads keep trying -> no waiting, better throughput
- Eliminates issues: deadlock, priority inversion, thread contention

## Key Features

- **Lock-free**: Uses CAS (Compare-And-Swap) instead of locks - better performance under high concurrency
- **Non-blocking**: poll() returns immediately (null if empty) - threads never wait
- **Unbounded**: Dynamically grows as needed - limited only by memory
- **Thread-safe**: Multiple producers and consumers can operate simultaneously without external synchronization
- **No null**: Cannot add null elements - uses null to indicate empty queue
- **FIFO ordering**: Maintains insertion order strictly

## When to Use

**Use when:**
- High concurrency with many threads (lock-free shines here)
- Non-blocking operations needed (can't afford to wait)
- Producer-consumer pattern with varying speeds
- Throughput is more important than latency
- Unbounded growth is acceptable

**Avoid when:**
- Need bounded queue to prevent memory issues (use ArrayBlockingQueue)
- Need blocking operations like take() that waits for elements (use LinkedBlockingQueue)
- Single-threaded or low concurrency (regular LinkedList is simpler)
- Need strict size limits or backpressure control
- Frequently need to check size() - it's O(n) operation

## Basic Example

```java
ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

// Producer
new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        queue.offer("Task-" + i);
    }
}).start();

// Consumer
new Thread(() -> {
    while (true) {
        String task = queue.poll();
        if (task != null) {
            System.out.println("Processing: " + task);
        }
    }
}).start();
```

## Common Operations

```java
ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

// Add (never fails)
queue.offer("item");

// Remove and return (null if empty)
String item = queue.poll();

// Peek without removing (null if empty)
String head = queue.peek();

// Check if empty
boolean empty = queue.isEmpty();

// Size (O(n) - expensive!)
int size = queue.size();
```

## Real-World Example

```java
public class OrderQueue {
    private ConcurrentLinkedQueue<Order> queue = new ConcurrentLinkedQueue<>();
    
    // Cashiers add orders
    public void addOrder(Order order) {
        queue.offer(order);
    }
    
    // Kitchen processes orders
    public void processNext() {
        Order order = queue.poll();
        if (order != null) {
            System.out.println("Processing: " + order.getId());
        }
    }
}
```

## vs Other Queues

| Queue | Locking | Bounded | Blocking |
|-------|---------|---------|----------|
| ConcurrentLinkedQueue | Lock-free | No | No |
| LinkedBlockingQueue | Lock-based | Optional | Yes |
| ArrayBlockingQueue | Lock-based | Yes | Yes |

## How It Works

Uses **CAS (Compare-And-Swap)** - atomic operation that updates value only if current value matches expected value. If it fails, retry.

```java
// Simplified logic
while (true) {
    if (compareAndSwap(expected, newValue)) {
        return true; // Success
    }
    // Retry if another thread modified it
}
```

## Important Notes

- **size() is O(n)** - avoid in hot paths, use isEmpty() instead
- **poll() doesn't block** - returns null immediately if empty
- **No null elements** - throws NullPointerException
- **Weakly consistent iterator** - may not see concurrent modifications

## Quick Reference

```java
// Create
ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();

// Add
queue.offer(item);

// Remove
T item = queue.poll(); // null if empty

// Peek
T head = queue.peek(); // null if empty

// Empty check
if (queue.isEmpty()) { }
```