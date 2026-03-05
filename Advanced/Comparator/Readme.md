# Comparator Example

A demonstration of Java's `Comparator` interface, used for custom sorting of objects.

## Overview

The `Comparator` interface is used to define an external sorting strategy for objects. Unlike `Comparable`, which defines the natural ordering of a class, `Comparator` allows you to sort objects based on different criteria without modifying the class itself.

## What This Example Demonstrates

- **Custom Sorting**: Sorting a list of strings by length or lexicographically.
- **Reverse Ordering**: Sorting in descending order.
- **Multiple Criteria**: Sorting by different attributes of an object.
- **Java 8 Lambdas**: Using lambda expressions for concise comparator definitions.

## Key Methods

| Method | Description |
| :--- | :--- |
| `compare(T o1, T o2)` | Compares its two arguments for order. Returns negative, zero, or positive integer. |
| `reversed()` | Returns a comparator that imposes the reverse ordering of this comparator. |
| `thenComparing()` | Returns a lexicographical-order comparator with another comparator. |
| `naturalOrder()` | Returns a comparator that compares `Comparable` objects in natural order. |
| `reverseOrder()` | Returns a comparator that imposes the reverse of natural ordering. |

## How It Works

### Traditional Comparator
```java
Comparator<String> lengthComparator = new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return Integer.compare(s1.length(), s2.length());
    }
};
```

### Java 8 Lambda
```java
Comparator<String> lambdaComparator = (s1, s2) -> Integer.compare(s1.length(), s2.length());
```

## Comparator vs Comparable

| Feature | Comparable | Comparator |
| :--- | :--- | :--- |
| **Interface** | `java.lang.Comparable` | `java.util.Comparator` |
| **Method** | `compareTo(T o)` | `compare(T o1, T o2)` |
| **Logic Location** | Defined inside the class being sorted. | Defined in a separate class or as a lambda. |
| **Flexibility** | Provides only one "natural" sort. | Can provide multiple sorting options. |
| **Change** | Requires modifying the target class. | Does not require modifying the target class. |

## Use Cases

- **Sorting Collections**: Sorting `ArrayList`, `TreeSet`, or `TreeMap` by custom fields.
- **API Responses**: Ordering results by date, price, or relevance.
- **Priority Queues**: Defining custom priorities for elements.
