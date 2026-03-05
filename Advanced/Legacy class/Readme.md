# Legacy Collections Example

A demonstration of Java's legacy collection classes from the early days of JDK 1.0/1.1.

## Overview

Legacy collections were present in Java before the Collections Framework was introduced in JDK 1.2. While they have been retrofitted to support the modern interfaces, they are generally avoided in favor of newer implementations unless thread-safety is required and you cannot use `java.util.concurrent`.

## What This Example Demonstrates

- **Vector**: A synchronized, resizable array.
- **Stack**: A subclass of `Vector` that implements a standard LIFO stack.
- **Hashtable**: A synchronized hash table implementation of the `Map` interface.
- **Enumeration**: The legacy version of `Iterator`.

## Legacy vs Modern Counterparts

| Legacy | Modern Replacement | Key Difference |
| :--- | :--- | :--- |
| `Vector` | `ArrayList` | `Vector` is synchronized (thread-safe but slower). |
| `Hashtable` | `HashMap` | `Hashtable` is synchronized and doesn't allow `null`. |
| `Stack` | `Deque` / `ArrayDeque` | `Stack` is legacy and extends `Vector`; `Deque` is preferred. |
| `Enumeration` | `Iterator` | `Iterator` allows removing elements during traversal. |

## Key Methods (Legacy)

### Vector / Stack
- `addElement(E obj)`: Legacy version of `add()`.
- `push(E item)`: Pushes an item onto the top of the stack.
- `pop()`: Removes and returns the top item.
- `peek()`: Returns the top item without removing it.

### Hashtable
- `put(K key, V value)`: Synchronized insertion.
- `elements()`: Returns an enumeration of the values.

## Why They are "Legacy"

1. **Overhead**: Every method is synchronized, which slows down performance even if you're only using them in a single thread.
2. **Design Flaws**: For example, `Stack` extends `Vector`, which means it has methods that a stack shouldn't have (like adding at a specific index).
3. **Better Alternatives**: The `java.util.concurrent` package provides better thread-safe alternatives like `CopyOnWriteArrayList` and `ConcurrentHashMap`.

## Use Cases

- **Legacy Code Maintenance**: Interacting with older APIs that return or expect these types.
- **Thread-Safety (Simple)**: In quick scripts where you need thread-safety without using the `concurrent` package.
