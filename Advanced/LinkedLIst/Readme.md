# LinkedList Example

A comprehensive demonstration of Java's `LinkedList`, which implements the `List` and `Deque` interfaces.

## Overview

`LinkedList` is a linear data structure where elements are not stored in contiguous memory locations. Instead, each element (node) contains a reference to the previous and next nodes.

## What This Example Demonstrates

- **Creation**: How to instantiate a `LinkedList`.
- **Addition**: Adding elements at the beginning, end, or specific positions.
- **Access**: Retrieving first, last, or specific elements.
- **Iteration**: Traversing the list using various methods.
- **Removal**: Removing elements from different positions.
- **Searching**: Checking if an element exists and finding its index.

## Key Methods

| Method | Description |
| :--- | :--- |
| `addFirst(E e)` | Inserts the specified element at the beginning of the list. |
| `addLast(E e)` | Appends the specified element to the end of the list. |
| `getFirst()` | Returns the first element in the list. |
| `getLast()` | Returns the last element in the list. |
| `removeFirst()` | Removes and returns the first element from the list. |
| `removeLast()` | Removes and returns the last element from the list. |
| `peek()` | Retrieves, but does not remove, the head of the list. |
| `poll()` | Retrieves and removes the head of the list. |

## How It Works

### Creation and Insertion
```java
LinkedList<String> animals = new LinkedList<>();
animals.add("Dog");
animals.addFirst("Cat");
animals.addLast("Elephant");
```

### Searching and Iteration
```java
boolean exists = animals.contains("Dog");
int index = animals.indexOf("Dog");
```

## Why LinkedList?

- **Fast Modifications**: O(1) for adding or removing elements at the ends.
- **No Resizing**: Unlike `ArrayList`, it doesn't need to resize or shift elements.
- **Memory**: Uses more memory per element (to store node references).
- **Deque Implementation**: Can be used as a List, Stack, or Queue.

**Trade-offs:**
- **Slow Access**: O(n) for random access (must traverse from head/tail).
- **Cache Locality**: Poor cache performance compared to `ArrayList`.

## Use Cases

- **Queue/Deque**: Implementing FIFO or LIFO structures.
- **Frequent Insertions/Deletions**: When most operations happen at the ends of the collection.
- **Memory Flexibility**: When you want to avoid large contiguous memory allocations.
