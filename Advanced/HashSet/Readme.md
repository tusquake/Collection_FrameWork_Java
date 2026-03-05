# HashSet Example

A demonstration of Java's `HashSet`, which implements the `Set` interface and is backed by a hash table (actually a `HashMap` instance).

## Overview

`HashSet` is used to store unique elements. It doesn't guarantee the iteration order of the set and permits the `null` element.

## What This Example Demonstrates

- **Unique Elements**: Automatic handling of duplicates (duplicate additions are ignored).
- **Heterogeneous Elements**: Storing different types of objects (String, Integer, Double, Boolean).
- **Iterator**: Using an `Iterator` to traverse the set.
- **Basic Operations**: `add()`, `remove()`, `contains()`, and `size()`.
- **Conversion**: Creating a `HashSet` from a `List`.

## Key Methods

| Method | Description |
| :--- | :--- |
| `add(E e)` | Adds the specified element if it is not already present. |
| `remove(Object o)` | Removes the specified element from this set if it is present. |
| `contains(Object o)` | Returns `true` if this set contains the specified element. |
| `size()` | Returns the number of elements in this set. |
| `clear()` | Removes all of the elements from this set. |
| `isEmpty()` | Returns `true` if this set contains no elements. |

## How It Works

### Handling Duplicates
```java
HashSet hs = new HashSet();
hs.add("Tushar");
hs.add("Tushar"); // This duplicate will be ignored
System.out.println(hs); // "Tushar" appears only once
```

### Iteration
```java
Iterator itr = hs.iterator();
while (itr.hasNext()) {
    System.out.println(itr.next());
}
```

## Why HashSet?

- **Unique Elements**: Best when you need a collection with no duplicates.
- **Performance**: Provides constant-time performance O(1) for basic operations like `add`, `remove`, `contains`, and `size`.
- **Unordered**: Does not maintain any order of elements.
- **Null Support**: Allows one `null` element.

**Trade-offs:**
- **No Order**: If you need insertion order, use `LinkedHashSet`. If you need sorted order, use `TreeSet`.
- **Not Thread-Safe**: For concurrent environments, use `ConcurrentHashMap.newKeySet()` or `Collections.synchronizedSet()`.

## Internal Mechanism

`HashSet` internally uses a `HashMap` to store its elements. Each element added to the `HashSet` is stored as a **Key** in the internal `HashMap`, while a dummy constant (called `PRESENT`) is used as the **Value**.

## Use Cases

- **Duplicate Removal**: Filtering out duplicates from a list.
- **Membership Checking**: Efficiently checking if an item exists in a large collection.
- **Data Deduplication**: Ensuring uniqueness in data processing tasks.
