# ConcurrentHashMap Login Counter Example

A practical demonstration of thread-safe concurrent operations using Java's `ConcurrentHashMap`.

## 📋 Overview

This example simulates a real-world scenario where multiple threads concurrently update a user's login count. It showcases how `ConcurrentHashMap` handles race conditions without explicit synchronization.

## 🎯 What This Example Demonstrates

- Thread-safe map operations in a multi-threaded environment
- Using the `merge()` method for atomic updates
- Proper thread management with `start()` and `join()`
- Avoiding race conditions without manual locking

## 🔧 How It Works

### The Setup

```java
ConcurrentHashMap<String, Integer> loginCountMap = new ConcurrentHashMap<>();
```

Creates a thread-safe hash map that can be safely accessed by multiple threads simultaneously.

### The Task

Each thread executes a task that increments the login count 1000 times:

```java
Runnable task = () -> {
    for (int i = 0; i < 1000; i++) {
        loginCountMap.merge("tushar", 1, Integer::sum);
    }
};
```

### Thread Execution

Three threads are created and executed concurrently:

```java
Thread t1 = new Thread(task);
Thread t2 = new Thread(task);
Thread t3 = new Thread(task);

t1.start();  // Starts thread 1
t2.start();  // Starts thread 2
t3.start();  // Starts thread 3

t1.join();   // Waits for thread 1 to complete
t2.join();   // Waits for thread 2 to complete
t3.join();   // Waits for thread 3 to complete
```

## 🔑 Key Method: merge()

The `merge()` method is crucial for thread-safe updates:

```java
loginCountMap.merge("tushar", 1, Integer::sum);
```

**Parameters:**
- `"tushar"` - The key (username)
- `1` - The value to merge
- `Integer::sum` - The remapping function (adds values together)

**How it works:**
1. If the key doesn't exist, it inserts the value (1)
2. If the key exists, it applies the function: `oldValue + newValue`
3. The entire operation is **atomic** (thread-safe)

## 📊 Expected Output

```
Final login count: 3000
```

Since each of the 3 threads increments the counter 1000 times, the final count is always **3000** — guaranteed by `ConcurrentHashMap`'s thread-safety.

## 💡 Why ConcurrentHashMap?

### Without ConcurrentHashMap (using HashMap)
If you used a regular `HashMap`, you would face:
- **Race conditions**: Multiple threads updating simultaneously
- **Lost updates**: Some increments might be lost
- **Inconsistent results**: Final count could be less than 3000

### With ConcurrentHashMap
- ✅ Thread-safe operations
- ✅ No explicit synchronization needed
- ✅ Better performance than `Hashtable` or `Collections.synchronizedMap()`
- ✅ Consistent and correct results

## 🏗️ ConcurrentHashMap Architecture

**Key features:**
- **Segmented locking**: Only locks portions of the map, not the entire structure
- **Lock-free reads**: Multiple threads can read without blocking
- **Atomic operations**: Methods like `merge()`, `compute()`, `putIfAbsent()` are atomic
- **High concurrency**: Optimized for concurrent access


## 🔄 Alternative Approaches

### Using compute()
```java
loginCountMap.compute("tushar", (key, value) -> 
    value == null ? 1 : value + 1
);
```

### Using computeIfPresent() and putIfAbsent()
```java
loginCountMap.putIfAbsent("tushar", 0);
loginCountMap.computeIfPresent("tushar", (key, value) -> value + 1);
```

### Using getOrDefault() with put() (NOT thread-safe)
```java
// ❌ This is NOT thread-safe!
int count = loginCountMap.getOrDefault("tushar", 0);
loginCountMap.put("tushar", count + 1);
```

## 📚 Real-World Use Cases

- **User session tracking**: Counting active sessions per user
- **API rate limiting**: Tracking requests per client
- **Cache implementation**: Thread-safe caching mechanisms
- **Real-time analytics**: Concurrent event counting
- **Web server metrics**: Request counting across threads

## ⚠️ Important Notes

1. **Use atomic operations**: Always use methods like `merge()`, `compute()`, or `computeIfAbsent()` for updates
2. **Avoid compound operations**: Don't combine `get()` and `put()` separately — they're not atomic together
3. **Null values not allowed**: `ConcurrentHashMap` doesn't support null keys or values
4. **Iterators are weakly consistent**: They reflect the state at some point but don't throw `ConcurrentModificationException`