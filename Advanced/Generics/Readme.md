# Generics Example

A demonstration of Java Generics, providing compile-time type safety for classes and methods.

## Overview

Java Generics allow you to parameterize types. By using Generics, you can create classes, interfaces, and methods that work with different data types while ensuring type safety at compile time.

## What This Example Demonstrates

- **Generic Classes**: Creating a `Printer<T>` class that can print any type of object.
- **Type Safety**: Avoiding `ClassCastException` by specifying the allowed types.
- **Code Reuse**: Reducing the need for duplicate logic (e.g., separate printers for Integer and Double).
- **Wildcards**: Using `?` to represent unknown types.

## Key Concepts

| Concept | Syntax | Description |
| :--- | :--- | :--- |
| **Type Parameter** | `<T>` | A placeholder for a type (e.g., T for Type, E for Element, K for Key). |
| **Bounded Types** | `<T extends Number>` | Limits the type parameter to a specific class or its subclasses. |
| **Wildcards** | `List<?>` | Represents an unknown type. |
| **Generic Methods** | `<T> void print(T item)` | Methods that define their own type parameters. |

## How It Works

### Generic Class
```java
public class Printer<T> {
    T thingToPrint;
    
    public Printer(T thingToPrint) {
        this.thingToPrint = thingToPrint;
    }
    
    public void print() {
        System.out.println(thingToPrint);
    }
}
```

### Usage
```java
Printer<Integer> intPrinter = new Printer<>(123);
intPrinter.print();

Printer<String> stringPrinter = new Printer<>("Hello");
stringPrinter.print();
```

## Why Use Generics?

- **Type Safety**: Errors are caught at compile time instead of runtime.
- **Elimination of Casts**: No need to manually cast objects when retrieving them from a collection.
- **Reusable Code**: Write once, work with many types.
- **Better Performance**: Avoids boxing/unboxing overhead in some cases and provides hints to the compiler for optimization.

## Use Cases

- **Collections Framework**: `ArrayList<E>`, `HashMap<K, V>`.
- **Data Wrappers**: Creating generic Result or Response objects for APIs.
- **Utility Libraries**: Writing algorithms that work on any type of data.
