# ArrayBlockingQueue

## What is it?

Thread-safe, **bounded** blocking queue backed by a **fixed-size array**. Always has a capacity limit.

**Core Concept**: Producers block when array is full, consumers block when array is empty. Uses a single lock for both operations.

## Key Features

- **Bounded**: Fixed capacity set at creation (cannot change)
- **Blocking**: Threads wait when full/empty
- **Thread-safe**: Multiple producers/consumers
- **Single lock**: One lock for all operations (simpler but less concurrent)
- **FIFO ordering**: First In, First Out
- **Fair mode**: Optional fair ordering of waiting threads

## Basic Example

```java
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        // Fixed capacity of 3
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        
        // Producer
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Producing: " + i);
                    queue.put(i);  // BLOCKS if queue is full
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        // Consumer (slower)
        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Integer value = queue.take();  // BLOCKS if empty
                    System.out.println("Consuming: " + value);
                    Thread.sleep(500);
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
Producing: 2
Producing: 3     → Queue: [1, 2, 3] (FULL - capacity 3)
Producing: 4     → Producer BLOCKS (waiting for space)

Consuming: 1     → Queue: [2, 3] (space available)
Producing: 4     → Producer UNBLOCKS, Queue: [2, 3, 4]
Producing: 5     → Producer BLOCKS again

Consuming: 2     → Queue: [3, 4]
Producing: 5     → Queue: [3, 4, 5]

Consuming: 3
Consuming: 4
Consuming: 5     → Queue: [] (empty)
```

## Common Operations

```java
BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

// Blocking operations
queue.put("item");           // Blocks if full
String item = queue.take();  // Blocks if empty

// Timed blocking
queue.offer("item", 5, TimeUnit.SECONDS);  // Wait max 5 sec
String item = queue.poll(5, TimeUnit.SECONDS);

// Non-blocking
boolean added = queue.offer("item");  // false if full
String item = queue.poll();           // null if empty

// Utility
String head = queue.peek();
int size = queue.size();
int space = queue.remainingCapacity();
```

## Fair vs Unfair Mode

```java
// Unfair (default) - better throughput
BlockingQueue<String> unfair = new ArrayBlockingQueue<>(10);

// Fair - FIFO order for waiting threads (lower throughput)
BlockingQueue<String> fair = new ArrayBlockingQueue<>(10, true);
```

**Fair mode**: Threads are guaranteed to access queue in the order they requested access. Prevents starvation but reduces throughput.

## Real-World Example: Rate Limiter

```java
public class RequestRateLimiter {
    // Max 100 concurrent requests
    private BlockingQueue<Request> requestQueue = new ArrayBlockingQueue<>(100);
    
    // Accept requests (blocks if limit reached)
    public void submitRequest(Request request) {
        try {
            requestQueue.put(request);
            System.out.println("Request accepted: " + request.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Request rejected");
        }
    }
    
    // Process requests
    public void processRequests() {
        while (true) {
            try {
                Request request = requestQueue.take();
                System.out.println("Processing: " + request.getId());
                Thread.sleep(100);  // Process time
                System.out.println("Completed: " + request.getId());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        RequestRateLimiter limiter = new RequestRateLimiter();
        
        // Start processor
        new Thread(() -> limiter.processRequests()).start();
        
        // Submit requests
        for (int i = 1; i <= 150; i++) {
            final int id = i;
            new Thread(() -> limiter.submitRequest(new Request(id))).start();
        }
    }
}

class Request {
    private int id;
    public Request(int id) { this.id = id; }
    public int getId() { return id; }
}
```

## ArrayBlockingQueue vs LinkedBlockingQueue

| Feature | ArrayBlockingQueue | LinkedBlockingQueue |
|---------|-------------------|---------------------|
| Capacity | Always bounded | Optionally bounded |
| Storage | Fixed array | Linked nodes |
| Locks | One lock | Two locks (put/take) |
| Throughput | Lower | Higher |
| Memory | Lower (no nodes) | Higher (node overhead) |
| Fairness | Optional | No |
| Best for | Fixed capacity | Variable capacity |

## When to Use

**Use ArrayBlockingQueue when:**
- Need strict capacity limit (backpressure control)
- Memory efficiency matters (no node overhead)
- Want fair mode for thread ordering
- Capacity is known and fixed
- Rate limiting or throttling

**Avoid when:**
- Need variable capacity (use LinkedBlockingQueue)
- Need high throughput with many threads (use LinkedBlockingQueue)
- Capacity unknown or grows dynamically

## How It Works Internally

```
Internal Array (circular buffer):
  head              tail
    ↓                ↓
[C][D][E][ ][ ][A][B]
         ↑        ↑
      (empty)  (wraps)

Single ReentrantLock protects all operations
Condition variables: notEmpty, notFull
```

**put() operation:**
1. Acquire lock
2. If full, wait on notFull condition
3. Add element to array[tail]
4. Increment tail (circular)
5. Signal notEmpty condition
6. Release lock

**take() operation:**
1. Acquire lock
2. If empty, wait on notEmpty condition
3. Remove element from array[head]
4. Increment head (circular)
5. Signal notFull condition
6. Release lock

## Important Notes

**Capacity is fixed:**
```java
BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
// Capacity always 10, cannot grow
```

**Always handle interrupts:**
```java
try {
    queue.put(item);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

**Use for backpressure:**
```java
// Naturally limits incoming requests
BlockingQueue<Task> tasks = new ArrayBlockingQueue<>(1000);
// If producers try to add 1001st task, they block
```

**Fair mode trades throughput:**
```java
// Unfair: Better performance
new ArrayBlockingQueue<>(100);

// Fair: Prevents starvation, slower
new ArrayBlockingQueue<>(100, true);
```

## Real-World Example: Thread Pool Work Queue

```java
public class CustomThreadPool {
    private BlockingQueue<Runnable> workQueue;
    private List<Thread> workers;
    
    public CustomThreadPool(int threadCount, int queueSize) {
        workQueue = new ArrayBlockingQueue<>(queueSize);
        workers = new ArrayList<>();
        
        for (int i = 0; i < threadCount; i++) {
            Thread worker = new Thread(() -> {
                while (true) {
                    try {
                        Runnable task = workQueue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            worker.start();
            workers.add(worker);
        }
    }
    
    public void submit(Runnable task) throws InterruptedException {
        workQueue.put(task);  // Blocks if queue full
    }
}
```

## Testing

```java
@Test
public void testBlockingBehavior() throws InterruptedException {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
    
    queue.put(1);
    queue.put(2);
    
    // Queue full - should timeout
    boolean added = queue.offer(3, 100, TimeUnit.MILLISECONDS);
    assertFalse(added);
    
    // Remove one
    assertEquals(1, queue.take().intValue());
    
    // Now should succeed
    assertTrue(queue.offer(3));
    assertEquals(2, queue.size());
}
```

## Quick Reference

```java
// Create
BlockingQueue<T> queue = new ArrayBlockingQueue<>(capacity);
BlockingQueue<T> fair = new ArrayBlockingQueue<>(capacity, true);

// Blocking
queue.put(item);              // Wait if full
T item = queue.take();        // Wait if empty

// Timed
queue.offer(item, 5, TimeUnit.SECONDS);
T item = queue.poll(5, TimeUnit.SECONDS);

// Non-blocking
boolean ok = queue.offer(item);  // false if full
T item = queue.poll();           // null if empty

// Utility
queue.remainingCapacity();
queue.size();
queue.peek();
```

## Summary

- **Always bounded** - fixed capacity, cannot change
- **Single lock** - simpler but lower concurrency than LinkedBlockingQueue
- **Fair mode** - optional FIFO ordering of waiting threads
- **Memory efficient** - array storage, no node overhead
- **Perfect for** - rate limiting, backpressure, fixed capacity buffers
- **Use put/take** - for blocking operations
- **Use offer/poll** - for non-blocking with timeout