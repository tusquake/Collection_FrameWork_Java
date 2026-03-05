# Map Interface Example

A demonstration of the `Map` interface in Java, which is the root of the map hierarchy.

## Overview

A `Map` is an object that maps keys to values. A map cannot contain duplicate keys; each key can map to at most one value.

## What This Example Demonstrates

- **Contract**: The basic methods defined in the `Map` interface.
- **Implementations**: While `Map` is an interface, it is demonstrated using common implementations like `HashMap`.
- **Iteration**: Using `entrySet()`, `keySet()`, and `values()` to access map content.
- **Functional Methods**: Using Java 8 methods like `forEach`, `getOrDefault`, and `putIfAbsent`.

## Key Methods

| Method | Description |
| :--- | :--- |
| `size()` | Returns the number of key-value mappings in this map. |
| `isEmpty()` | Returns `true` if this map contains no key-value mappings. |
| `containsKey(Object key)` | Returns `true` if this map contains a mapping for the specified key. |
| `containsValue(Object value)` | Returns `true` if this map maps one or more keys to the specified value. |
| `get(Object key)` | Returns the value to which the specified key is mapped. |
| `put(K key, V value)` | Associates the specified value with the specified key in this map. |
| `remove(Object key)` | Removes the mapping for a key from this map if it is present. |
| `putAll(Map<? extends K, ? extends V> m)` | Copies all of the mappings from the specified map to this map. |
| `clear()` | Removes all of the mappings from this map. |

## Why Use the Map Interface?

- **Abstraction**: Writing code against the `Map` interface allows you to switch between `HashMap`, `TreeMap`, or `LinkedHashMap` without changing much code.
- **Universal Contract**: Ensures consistency across different map implementations.

## Common Implementations

1. **HashMap**: Best all-around performance, unordered.
2. **LinkedHashMap**: Maintains insertion order.
3. **TreeMap**: Maintains sorted order of keys.
4. **Hashtable**: Legacy, synchronized implementation.
5. **ConcurrentHashMap**: High-concurrency, thread-safe implementation.
