# TreeMap Example

A demonstration of Java's `TreeMap`, a `Red-Black tree` based `NavigableMap` implementation.

## Overview

`TreeMap` stores data in **Key-Value pairs** and keeps the keys in **sorted order**. It is part of the `SortedMap` and `NavigableMap` interfaces.

## What This Example Demonstrates

- **Sorted Mappings**: Elements are automatically sorted based on the natural ordering of keys or a provided `Comparator`.
- **Navigable Operations**: Methods for retrieving closest matches (higher, lower, ceiling, floor).
- **Basic Operations**: `put()`, `get()`, `remove()`.
- **First/Last Access**: Retrieving the first and last keys/entries in the sorted map.
- **Descending Order**: Getting a reverse-order view of the map.

## Key Methods

| Method | Description |
| :--- | :--- |
| `put(K key, V value)` | Associates the specified value with the specified key. |
| `firstKey()` | Returns the first (lowest) key currently in this map. |
| `lastKey()` | Returns the last (highest) key currently in this map. |
| `higherKey(K key)` | Returns the least key strictly greater than the given key. |
| `lowerKey(K key)` | Returns the greatest key strictly less than the given key. |
| `ceilingKey(K key)` | Returns the least key greater than or equal to the given key. |
| `floorKey(K key)` | Returns the greatest key less than or equal to the given key. |
| `descendingMap()` | Returns a reverse order view of the mappings. |

## How It Works

### Sorted Insertion
```java
TreeMap<Integer, String> treeMap = new TreeMap<>();
treeMap.put(10, "Ten");
treeMap.put(5, "Five");
treeMap.put(15, "Fifteen");
// Keys will be sorted as: 5, 10, 15
```

### Navigability
```java
System.out.println(treeMap.higherKey(5)); // Output: 10
System.out.println(treeMap.firstKey());   // Output: 5
```

## Why TreeMap?

- **Sorted Keys**: Perfect when you need to maintain a sorted order of mappings.
- **Navigability**: Provides efficient ways to find elements based on their relative order.
- **Performance**: Guaranteed O(log n) time complexity for `get`, `put`, and `remove`.

**Trade-offs:**
- **No Null Keys**: Does not permit `null` keys (will throw `NullPointerException`).
- **Slower than HashMap**: O(log n) vs O(1) for basic operations.

## Use Cases

- **Dictionary/Glossary**: Maintaining entries in alphabetical order.
- **Range Queries**: Finding all entries within a specific range of keys.
- **Ordered Statistics**: Keeping track of scores or rankings.
