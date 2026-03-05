# TreeSet Example

A demonstration of Java's `TreeSet`, which implements the `NavigableSet` interface and is backed by a `TreeMap`.

## Overview

`TreeSet` is a collection that stores **unique elements** in **sorted order**. It uses a `Red-Black tree` to maintain the ordering.

## What This Example Demonstrates

- **Sorted Elements**: Elements are stored in their natural ascending order (or by a custom `Comparator`).
- **Unique Elements**: Duplicate elements are not allowed.
- **Navigable Methods**: Finding elements relative to others (higher, lower, ceiling, floor).
- **First/Last Element**: Retrieving the minimum and maximum elements in the set.

## Key Methods

| Method | Description |
| :--- | :--- |
| `add(E e)` | Adds the element if it's not present and sorts it. |
| `first()` | Returns the first (lowest) element. |
| `last()` | Returns the last (highest) element. |
| `higher(E e)` | Returns the least element strictly greater than the given element. |
| `lower(E e)` | Returns the greatest element strictly less than the given element. |
| `ceiling(E e)` | Returns the least element greater than or equal to the given element. |
| `floor(E e)` | Returns the greatest element less than or equal to the given element. |
| `descendingSet()` | Returns a reverse order view of the elements. |

## How It Works

### Sorted Set
```java
TreeSet<Integer> set = new TreeSet<>();
set.add(40);
set.add(10);
set.add(30);
// Elements will be: 10, 30, 40
```

### Navigation
```java
System.out.println(set.higher(30)); // Output: 40
System.out.println(set.first());    // Output: 10
```

## Why TreeSet?

- **Natural Sorting**: Automatic sorting on insertion.
- **Navigability**: Efficient range-based operations and proximity searching.
- **Performance**: O(log n) for basic operations like `add`, `remove`, and `contains`.

**Trade-offs:**
- **No Null Elements**: Throws `NullPointerException` if you try to add `null` (because it needs to compare elements for sorting).
- **Slower than HashSet**: O(log n) vs O(1) for basic operations.

## Use Cases

- **Maintaining Sorted Unique Lists**: Like usernames in alphabetical order.
- **Range Checks**: Finding all numbers in a set between X and Y.
- **Priority Data**: Accessing the smallest or largest item efficiently.
