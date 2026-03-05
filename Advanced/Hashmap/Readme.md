# HashMap Example

A demonstration of Java's `HashMap`, a hash table-based implementation of the `Map` interface.

## Overview

`HashMap` stores data in **Key-Value pairs**. It provides high-performance access to values if the key is known.

## What This Example Demonstrates

- **Key-Value Storage**: Storing and retrieving mappings.
- **Heterogeneous Data**: Using different types for keys and values.
- **Basic Operations**: `put()`, `get()`, `remove()`, `containsKey()`, and `size()`.
- **Iteration**: Traversing keys, values, or entries.
- **Handling Nulls**: Support for one `null` key and multiple `null` values.

## Key Methods

| Method | Description |
| :--- | :--- |
| `put(K key, V value)` | Associates the specified value with the specified key. |
| `get(Object key)` | Returns the value to which the specified key is mapped. |
| `remove(Object key)` | Removes the mapping for the specified key. |
| `containsKey(Object key)` | Returns `true` if this map contains a mapping for the key. |
| `keySet()` | Returns a `Set` view of the keys contained in the map. |
| `entrySet()` | Returns a `Set` view of the mappings (Entry objects). |
| `values()` | Returns a `Collection` view of the values contained in the map. |

## How It Works

### Basic Operations
```java
HashMap<Integer, String> map = new HashMap<>();
map.put(1, "Java");
map.put(2, "Python");
System.out.println(map.get(1)); // Output: Java
```

### Iteration
```java
for (Map.Entry<Integer, String> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " : " + entry.getValue());
}
```

## Why HashMap?

- **Performance**: Average O(1) time complexity for `get()` and `put()`.
- **Unordered**: Does not guarantee any specific order of mappings.
- **Null Support**: Allows one `null` key and multiple `null` values.

**Trade-offs:**
- **Not Thread-Safe**: Use `ConcurrentHashMap` for multi-threaded applications.
- **No Order**: If you need insertion order, use `LinkedHashMap`. For sorted order, use `TreeMap`.

## Internal Mechanism

- **Hashing**: Uses the `hashCode()` of the key to determine the bucket location.
- **Collision Handling**: Uses LinkedList (and Trees in Java 8+) for handling hash collisions.
- **Buckets**: Maintains an array of buckets.

## Use Cases

- **Caching**: Storing frequently accessed data for quick retrieval.
- **Indexing**: Creating a lookup table for objects.
- **Configuration Maps**: Storing settings as key-value pairs.
