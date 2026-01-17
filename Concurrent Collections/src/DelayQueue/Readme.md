# DelayQueue

## What is it?

Thread-safe, **unbounded** blocking queue where elements can only be retrieved **after their delay expires**. Uses priority queue internally.

**Core Concept**: Elements become available only after a specified delay time. Perfect for scheduling tasks to run in the future.

## Key Features

- **Delay-based**: Elements can't be taken until delay expires
- **Unbounded**: No capacity limit (grows as needed)
- **Blocking**: Blocks until element is ready to be consumed
- **Thread-safe**: Multiple producers/consumers
- **Priority ordering**: Elements ordered by expiration time (earliest first)
- **Requires Delayed interface**: Elements must implement Delayed

## Delayed Interface

```java
public interface Delayed extends Comparable<Delayed> {
    long getDelay(TimeUnit unit);
}
```

Elements must:
1. Implement `getDelay()` - return remaining delay
2. Implement `compareTo()` - compare delays (for ordering)

## Basic Example

```java
import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

class DelayedTask implements Delayed {
    private String name;
    private long startTime;  // When task becomes available
    
    public DelayedTask(String name, long delayInMillis) {
        this.name = name;
        this.startTime = System.currentTimeMillis() + delayInMillis;
    }
    
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.startTime, ((DelayedTask) o).startTime);
    }
    
    public String getName() { return name; }
}

public class DelayQueueExample {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        
        // Add tasks with different delays
        queue.put(new DelayedTask("Task 1", 3000));  // 3 seconds
        queue.put(new DelayedTask("Task 2", 1000));  // 1 second
        queue.put(new DelayedTask("Task 3", 2000));  // 2 seconds
        
        System.out.println("Waiting for tasks...");
        
        // Tasks come out in delay order: Task 2, Task 3, Task 1
        while (!queue.isEmpty()) {
            DelayedTask task = queue.take();  // Blocks until delay expires
            System.out.println("Executing: " + task.getName());
        }
    }
}
```

## Common Operations

```java
DelayQueue<DelayedTask> queue = new DelayQueue<>();

// Add (never blocks, unbounded)
queue.put(delayedTask);
queue.offer(delayedTask);

// Take (blocks until element delay expires)
DelayedTask task = queue.take();

// Poll (returns null if no expired element)
DelayedTask task = queue.poll();

// Timed poll (waits for delay or timeout)
DelayedTask task = queue.poll(5, TimeUnit.SECONDS);

// Peek (view next expired element, null if none ready)
DelayedTask next = queue.peek();

// Size
int size = queue.size();
```

## Real-World Example: Cache with Expiration

```java
class CacheEntry implements Delayed {
    private String key;
    private String value;
    private long expiryTime;
    
    public CacheEntry(String key, String value, long ttlMillis) {
        this.key = key;
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + ttlMillis;
    }
    
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = expiryTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.expiryTime, ((CacheEntry) o).expiryTime);
    }
    
    public String getKey() { return key; }
    public String getValue() { return value; }
}

public class ExpiringCache {
    private Map<String, String> cache = new ConcurrentHashMap<>();
    private DelayQueue<CacheEntry> expiryQueue = new DelayQueue<>();
    
    public ExpiringCache() {
        // Cleanup thread removes expired entries
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    CacheEntry expired = expiryQueue.take();  // Blocks until entry expires
                    cache.remove(expired.getKey());
                    System.out.println("Expired: " + expired.getKey());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }
    
    public void put(String key, String value, long ttlMillis) {
        cache.put(key, value);
        expiryQueue.put(new CacheEntry(key, value, ttlMillis));
        System.out.println("Cached: " + key + " (TTL: " + ttlMillis + "ms)");
    }
    
    public String get(String key) {
        return cache.get(key);
    }
    
    public static void main(String[] args) throws InterruptedException {
        ExpiringCache cache = new ExpiringCache();
        
        cache.put("user:1", "Alice", 2000);   // Expires in 2 seconds
        cache.put("user:2", "Bob", 5000);     // Expires in 5 seconds
        cache.put("user:3", "Charlie", 3000); // Expires in 3 seconds
        
        Thread.sleep(6000);  // Wait for all to expire
        
        System.out.println("user:1 = " + cache.get("user:1"));  // null
        System.out.println("user:2 = " + cache.get("user:2"));  // null
    }
}
```

## Real-World Example: Task Scheduler

```java
class ScheduledTask implements Delayed {
    private String taskName;
    private Runnable action;
    private long executeTime;
    
    public ScheduledTask(String taskName, Runnable action, long delayMillis) {
        this.taskName = taskName;
        this.action = action;
        this.executeTime = System.currentTimeMillis() + delayMillis;
    }
    
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = executeTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.executeTime, ((ScheduledTask) o).executeTime);
    }
    
    public void execute() {
        System.out.println("Executing: " + taskName);
        action.run();
    }
}

public class TaskScheduler {
    private DelayQueue<ScheduledTask> taskQueue = new DelayQueue<>();
    
    public void schedule(String name, Runnable task, long delayMillis) {
        taskQueue.put(new ScheduledTask(name, task, delayMillis));
        System.out.println("Scheduled: " + name + " (delay: " + delayMillis + "ms)");
    }
    
    public void start() {
        Thread executor = new Thread(() -> {
            while (true) {
                try {
                    ScheduledTask task = taskQueue.take();  // Blocks until ready
                    task.execute();
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        executor.start();
    }
    
    public static void main(String[] args) throws InterruptedException {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.start();
        
        // Schedule tasks
        scheduler.schedule("Send email", 
            () -> System.out.println("Email sent!"), 2000);
        
        scheduler.schedule("Generate report", 
            () -> System.out.println("Report generated!"), 4000);
        
        scheduler.schedule("Backup database", 
            () -> System.out.println("Database backed up!"), 1000);
        
        Thread.sleep(5000);  // Let tasks execute
    }
}
```

## DelayQueue vs Others

| Feature | DelayQueue | PriorityBlockingQueue | LinkedBlockingQueue |
|---------|-----------|----------------------|---------------------|
| Ordering | Delay expiration | Priority | FIFO |
| Bounded | No (unbounded) | No (unbounded) | Optional |
| Blocks on take | Yes (until ready) | Yes (if empty) | Yes (if empty) |
| Element type | Must implement Delayed | Must be Comparable | Any |
| Best for | Scheduled tasks | Priority tasks | FIFO blocking |

## When to Use

**Use DelayQueue when:**
- Need to schedule tasks for future execution
- Cache expiration or TTL (Time To Live)
- Session timeout management
- Retry mechanisms with backoff
- Rate limiting with time windows
- Reminder systems

**Avoid when:**
- Don't need delays (use other queues)
- Need bounded capacity (DelayQueue is unbounded)
- All tasks execute immediately
- Need millisecond precision (system clock dependent)

## How It Works Internally

```
Internal Structure:

DelayQueue uses PriorityQueue internally
Elements ordered by delay expiration time (earliest first)

[Delayed] -> PriorityQueue (ordered by getDelay())
                    |
                    v
            Binary Heap (min-heap)
                    
take() operation:
1. Get element with earliest expiration
2. If delay > 0, wait for that duration
3. Remove and return element
4. Signal other waiting threads

Leader-Follower Pattern:
- One thread (leader) waits for next element
- Other threads (followers) wait indefinitely
- When leader finishes, a follower becomes leader
```

## Important Notes

**Must implement Delayed interface:**
```java
// BAD - doesn't implement Delayed
class Task {
    String name;
}
DelayQueue<Task> queue = new DelayQueue<>();  // Won't work!

// GOOD
class Task implements Delayed {
    @Override
    public long getDelay(TimeUnit unit) { ... }
    
    @Override
    public int compareTo(Delayed o) { ... }
}
```

**getDelay() must decrease over time:**
```java
// GOOD - delay decreases
@Override
public long getDelay(TimeUnit unit) {
    long remaining = expiryTime - System.currentTimeMillis();
    return unit.convert(remaining, TimeUnit.MILLISECONDS);
}

// BAD - constant delay
@Override
public long getDelay(TimeUnit unit) {
    return unit.convert(5000, TimeUnit.MILLISECONDS);  // Always 5 seconds!
}
```

**Unbounded - monitor size:**
```java
// Can grow indefinitely
if (queue.size() > MAX_SIZE) {
    // Handle overflow
}
```

**take() blocks until ready:**
```java
// Blocks until delay expires
DelayedTask task = queue.take();

// Non-blocking - returns null if nothing ready
DelayedTask task = queue.poll();
```

**System clock dependent:**
```java
// Affected by system clock changes
// Use System.nanoTime() for more precision
long startTime = System.nanoTime() + delayNanos;

@Override
public long getDelay(TimeUnit unit) {
    long diff = startTime - System.nanoTime();
    return unit.convert(diff, TimeUnit.NANOSECONDS);
}
```

## Real-World Example: Connection Pool Cleanup

```java
class IdleConnection implements Delayed {
    private Connection connection;
    private long idleTimeout;
    
    public IdleConnection(Connection connection, long timeoutMillis) {
        this.connection = connection;
        this.idleTimeout = System.currentTimeMillis() + timeoutMillis;
    }
    
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = idleTimeout - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.idleTimeout, ((IdleConnection) o).idleTimeout);
    }
    
    public Connection getConnection() { return connection; }
}

public class ConnectionPool {
    private DelayQueue<IdleConnection> idleConnections = new DelayQueue<>();
    private static final long IDLE_TIMEOUT = 30000;  // 30 seconds
    
    public ConnectionPool() {
        // Cleanup thread for idle connections
        Thread cleanup = new Thread(() -> {
            while (true) {
                try {
                    IdleConnection idle = idleConnections.take();
                    idle.getConnection().close();
                    System.out.println("Closed idle connection");
                } catch (Exception e) {
                    break;
                }
            }
        });
        cleanup.setDaemon(true);
        cleanup.start();
    }
    
    public void returnConnection(Connection conn) {
        idleConnections.put(new IdleConnection(conn, IDLE_TIMEOUT));
    }
}
```

## Testing

```java
@Test
public void testDelayOrdering() throws InterruptedException {
    DelayQueue<DelayedTask> queue = new DelayQueue<>();
    
    long now = System.currentTimeMillis();
    queue.put(new DelayedTask("Task 3", 300));
    queue.put(new DelayedTask("Task 1", 100));
    queue.put(new DelayedTask("Task 2", 200));
    
    // Should come out in delay order
    assertEquals("Task 1", queue.take().getName());
    assertEquals("Task 2", queue.take().getName());
    assertEquals("Task 3", queue.take().getName());
}

@Test
public void testBlockingUntilReady() throws InterruptedException {
    DelayQueue<DelayedTask> queue = new DelayQueue<>();
    queue.put(new DelayedTask("Task", 1000));  // 1 second delay
    
    long start = System.currentTimeMillis();
    queue.take();  // Should block for ~1 second
    long elapsed = System.currentTimeMillis() - start;
    
    assertTrue(elapsed >= 1000);
}
```

## Quick Reference

```java
// Create
DelayQueue<T extends Delayed> queue = new DelayQueue<>();

// Implement Delayed
class MyTask implements Delayed {
    long expiryTime;
    
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = expiryTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.expiryTime, ((MyTask) o).expiryTime);
    }
}

// Add (never blocks)
queue.put(task);

// Take (blocks until ready)
T task = queue.take();

// Poll (non-blocking)
T task = queue.poll();  // null if nothing ready

// Size
int size = queue.size();
```

## Summary

- **Delay-based** - elements available only after delay expires
- **Unbounded** - no capacity limit, can grow indefinitely
- **Blocks on take** - waits until element is ready
- **Ordered by expiration** - earliest expiring element first
- **Requires Delayed** - elements must implement Delayed interface
- **Perfect for** - scheduled tasks, cache expiration, timeouts
- **Monitor size** - unbounded can cause memory issues
- **System clock dependent** - use nanoTime() for precision