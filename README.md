# Java Collections Complete Guide

## Table of Contents

1. [Introduction](#introduction)
2. [ArrayList](#arraylist)
3. [Comparator](#comparator)
4. [LinkedList](#linkedlist)
5. [Vector](#vector)
6. [Stack](#stack)
7. [Map](#map)
8. [HashMap](#hashmap)
9. [LinkedHashMap](#linkedhashmap)
10. [LRU Cache](#lru-cache)
11. [WeakHashMap](#weakhashmap)
12. [Garbage Collection](#garbage-collection)
13. [Comparable](#comparable)
14. [SortedMap](#sortedmap)
15. [NavigableMap](#navigablemap)
16. [HashTable](#hashtable)
17. [ConcurrentHashMap](#concurrenthashmap)
18. [ConcurrentSkipListMap](#concurrentskiplistmap)
19. [EnumMap](#enummap)
20. [ImmutableMap](#immutablemap)
21. [Set](#set)
22. [CopyOnWriteArraySet](#copyonwritearrayset)
23. [Queue](#queue)
24. [Priority Queue](#priority-queue)
25. [Deque](#deque)
26. [BlockingQueue](#blockingqueue)
27. [LinkedBlockingQueue](#linkedblockingqueue)
28. [PriorityBlockingQueue](#priorityblockingqueue)
29. [SynchronousQueue](#synchronousqueue)
30. [DelayQueue](#delayqueue)
31. [ConcurrentLinkedQueue](#concurrentlinkedqueue)
32. [ConcurrentLinkedDeque](#concurrentlinkeddeque)
33. [Iterable](#iterable)
34. [Java 8 Core Concepts](#java-8-core-concepts)
35. [Lambda Expression](#lambda-expression)
36. [Predicate](#predicate)
37. [Function](#function)
38. [Consumer](#consumer)
39. [Supplier](#supplier)
40. [Stream](#stream)
41. [ParallelStream](#parallelstream)
42. [Collectors](#collectors)
43. [Primitive Streams](#primitive-streams)

---

## Introduction

The Java Collections Framework is a comprehensive library that provides ready-made data structures and algorithms. It offers a unified architecture for representing and manipulating collections, enabling programs to be more efficient, maintainable, and reusable.

**Key Benefits:**
- Reduces programming effort by providing reusable data structures
- Increases program performance through high-performance implementations
- Provides interoperability between unrelated APIs
- Reduces effort to learn and use new APIs
- Promotes software reuse

**Core Interfaces:**
- Collection (root interface)
- List (ordered collection, allows duplicates)
- Set (no duplicate elements)
- Map (key-value pairs)
- Queue (FIFO operations)

---

## ArrayList

ArrayList is a resizable array implementation of the List interface. It provides dynamic arrays that can grow and shrink as needed during runtime.

**Key Characteristics:**
- Dynamic size - can grow and shrink at runtime
- Index-based access with O(1) time complexity
- Maintains insertion order
- Allows null values and duplicate elements
- Not thread-safe (not synchronized)
- Default initial capacity is 10

**Internal Working:**
- Uses an internal array to store elements
- When capacity is exceeded, creates a new array (1.5x larger)
- Copies all elements to the new array

**Example:**
```java
ArrayList<String> list = new ArrayList<>();
list.add("Hello");
list.add("World");
list.add(1, "Beautiful"); // Insert at index 1
String element = list.get(0); // Returns "Hello"
list.remove(2); // Remove element at index 2
```

**When to Use:**
- Need frequent random access to elements
- Size is not fixed
- Thread safety is not required
- More reads than insertions/deletions in middle

**Advantages:**
- Fast random access (O(1))
- Dynamic sizing
- Memory efficient (no overhead for pointers)

**Disadvantages:**
- Slow insertion/deletion in middle (O(n))
- Fixed capacity needs resizing

---

## Comparator

Comparator is a functional interface that defines a comparison function to impose a total ordering on objects. It's used for custom sorting logic.

**Key Points:**
- Compares two objects and returns integer result
- Can override natural ordering of objects
- Supports lambda expressions (Java 8+)
- Can chain multiple comparators
- Used by sorting algorithms

**Methods:**
- compare(T o1, T o2) - main comparison method
- reversed() - returns reverse order comparator
- thenComparing() - chains comparators

**Examples:**
```java
// Using lambda expression
Comparator<Person> ageComparator = (p1, p2) -> p1.getAge() - p2.getAge();

// Using method reference
Comparator<Person> nameComparator = Comparator.comparing(Person::getName);

// Chaining comparators
Comparator<Person> complexComparator = Comparator
    .comparing(Person::getAge)
    .thenComparing(Person::getName);

// Sorting with comparator
Collections.sort(personList, ageComparator);

// With streams
List<Person> sortedPersons = personList.stream()
    .sorted(nameComparator)
    .collect(Collectors.toList());
```

**Use Cases:**
- Custom sorting requirements
- Multiple sorting criteria
- Reverse ordering
- Complex object comparisons

---

## LinkedList

LinkedList is a doubly-linked list implementation of List and Deque interfaces. Each element (node) contains data and references to the next and previous elements.

**Key Characteristics:**
- Dynamic size
- Sequential access - O(n) for random access
- Efficient insertion/deletion at beginning and end - O(1)
- Implements both List and Deque interfaces
- Not thread-safe

**Node Structure:**
```java
class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;
}
```

**Example:**
```java
LinkedList<String> list = new LinkedList<>();
list.addFirst("First");
list.addLast("Last");
list.add(1, "Middle");

// Deque operations
list.push("Stack Top");
String top = list.pop();

// Queue operations
list.offer("Queue End");
String front = list.poll();
```

**ArrayList vs LinkedList:**
| Feature | ArrayList | LinkedList |
|---------|-----------|------------|
| Random Access | O(1) | O(n) |
| Insertion/Deletion (middle) | O(n) | O(1) if node known |
| Insertion/Deletion (ends) | O(1) amortized | O(1) |
| Memory Overhead | Lower | Higher (pointers) |
| Cache Performance | Better | Worse |

**When to Use LinkedList:**
- Frequent insertions/deletions at beginning or end
- Don't need random access
- Implementing stack or queue
- Size varies significantly

---

## Vector

Vector is a legacy synchronized implementation of a dynamic array, similar to ArrayList but with thread safety built-in.

**Key Characteristics:**
- Synchronized (thread-safe)
- Legacy class (from Java 1.0)
- Similar to ArrayList but with synchronization overhead
- Default capacity growth is 100% (doubles in size)
- Allows null values

**Example:**
```java
Vector<Integer> vector = new Vector<>();
vector.add(10);
vector.add(20);
vector.addElement(30); // Legacy method

// Thread-safe operations
vector.ensureCapacity(100);
```

**Vector vs ArrayList:**
| Feature | Vector | ArrayList |
|---------|---------|-----------|
| Thread Safety | Synchronized | Not synchronized |
| Performance | Slower | Faster |
| Growth Policy | 100% (doubles) | 50% (1.5x) |
| Legacy | Yes | No |

**Modern Alternative:**
```java
// Instead of Vector, use:
List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());

// Or use concurrent collections:
List<String> concurrentList = new CopyOnWriteArrayList<>();
```

**Note:** Vector is generally avoided in modern Java development due to performance overhead and availability of better alternatives.

---

## Stack

Stack is a Last-In-First-Out (LIFO) data structure that extends Vector class. It represents a stack of objects with typical push and pop operations.

**Key Operations:**
- push(item) - adds element to top
- pop() - removes and returns top element
- peek() - returns top element without removing
- empty() - checks if stack is empty
- search(item) - returns position of item from top

**Example:**
```java
Stack<String> stack = new Stack<>();
stack.push("First");
stack.push("Second");
stack.push("Third");

String top = stack.peek(); // "Third" (doesn't remove)
String popped = stack.pop(); // "Third" (removes)
boolean isEmpty = stack.empty(); // false

int position = stack.search("First"); // Returns position from top
```

**Problems with Stack Class:**
- Extends Vector (inherits all Vector methods)
- Can break LIFO principle by using inherited methods
- Performance overhead due to synchronization
- Not consistent with modern collection design

**Modern Alternative - ArrayDeque:**
```java
Deque<String> stack = new ArrayDeque<>();
stack.push("First");
stack.push("Second");
String top = stack.pop(); // Better performance, no synchronization overhead
```

**Use Cases:**
- Function call management (call stack)
- Expression evaluation
- Undo operations
- Browser history
- Backtracking algorithms

---

## Map

Map is a core interface that represents a collection of key-value pairs. Each key maps to exactly one value, and keys must be unique.

**Key Characteristics:**
- Stores key-value pairs (entries)
- Keys must be unique, values can be duplicate
- No duplicate keys allowed
- One null key allowed (in most implementations)
- Not a subtype of Collection interface

**Core Methods:**
```java
V put(K key, V value)           // Add/update entry
V get(Object key)               // Retrieve value by key
V remove(Object key)            // Remove entry by key
boolean containsKey(Object key) // Check if key exists
boolean containsValue(Object value) // Check if value exists
Set<K> keySet()                 // Get all keys
Collection<V> values()          // Get all values
Set<Map.Entry<K,V>> entrySet() // Get all entries
int size()                      // Number of entries
boolean isEmpty()               // Check if empty
```

**Common Implementations:**
- HashMap - Hash table based, O(1) average performance
- LinkedHashMap - Maintains insertion order
- TreeMap - Sorted map based on keys
- ConcurrentHashMap - Thread-safe hash map
- Hashtable - Legacy synchronized implementation

**Example:**
```java
Map<String, Integer> map = new HashMap<>();
map.put("apple", 100);
map.put("banana", 80);
map.put("orange", 120);

// Retrieve values
Integer applePrice = map.get("apple"); // 100
Integer grapePrice = map.get("grape");  // null

// Check existence
if (map.containsKey("banana")) {
    System.out.println("Banana price: " + map.get("banana"));
}

// Iterate over entries
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}

// Using Java 8 forEach
map.forEach((key, value) -> System.out.println(key + " = " + value));
```

---

## HashMap

HashMap is a hash table based implementation of the Map interface, providing O(1) average time performance for basic operations.

**Key Characteristics:**
- Hash table based implementation
- O(1) average time complexity for get/put operations
- Allows one null key and multiple null values
- Does not maintain insertion order
- Not thread-safe
- Default initial capacity: 16, load factor: 0.75

**Internal Working:**
1. **Hashing:** Key's hashCode() determines bucket location
2. **Bucket:** Array index where key-value pair is stored
3. **Collision Handling:** Uses chaining (linked list) or tree structure
4. **Load Factor:** When size > capacity * load factor, rehashing occurs
5. **Rehashing:** Creates larger array and redistributes elements

**Hash Function:**
```java
index = hash(key) & (capacity - 1)
```

**Java 8 Improvements:**
- When chain length > 8, converts to balanced tree (Red-Black tree)
- Improves worst-case performance from O(n) to O(log n)

**Example:**
```java
HashMap<String, Integer> hashMap = new HashMap<>();

// Basic operations
hashMap.put("apple", 100);
hashMap.put("banana", 80);
hashMap.put("orange", 120);
hashMap.put(null, 50); // Null key allowed

// Retrieve and update
Integer price = hashMap.get("apple"); // 100
hashMap.put("apple", 110); // Update existing key

// Check operations
boolean hasApple = hashMap.containsKey("apple");
boolean hasPrice100 = hashMap.containsValue(100);

// Compute operations (Java 8+)
hashMap.computeIfAbsent("grape", k -> 90);
hashMap.computeIfPresent("apple", (k, v) -> v + 10);

// Merge operation
hashMap.merge("apple", 5, Integer::sum);
```

**Performance Considerations:**
- Good hash function distribution is crucial
- High load factor increases collision chances
- Rehashing is expensive but infrequent
- Initial capacity should be set appropriately for known data size

**When to Use:**
- Need fast key-based access
- Order doesn't matter
- Single-threaded environment
- Frequent get/put operations

---

## LinkedHashMap

LinkedHashMap extends HashMap and maintains a doubly-linked list of entries, preserving insertion order or access order.

**Key Characteristics:**
- Extends HashMap functionality
- Maintains insertion order by default
- Can maintain access order (LRU behavior)
- Slightly slower than HashMap due to linked list overhead
- Predictable iteration order

**Constructor Options:**
```java
// Insertion order (default)
LinkedHashMap<String, Integer> insertionOrder = new LinkedHashMap<>();

// Access order (LRU cache behavior)
LinkedHashMap<String, Integer> accessOrder = new LinkedHashMap<>(16, 0.75f, true);
```

**Example:**
```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
map.put("first", 1);
map.put("second", 2);
map.put("third", 3);

// Iteration maintains insertion order
for (String key : map.keySet()) {
    System.out.println(key); // Prints: first, second, third
}

// Access order example
LinkedHashMap<String, String> accessOrderMap = 
    new LinkedHashMap<>(16, 0.75f, true);
    
accessOrderMap.put("A", "Apple");
accessOrderMap.put("B", "Banana");
accessOrderMap.put("C", "Cherry");

accessOrderMap.get("A"); // Moves A to end (most recently accessed)
// Order now: B, C, A
```

**Access Order vs Insertion Order:**
```java
// Insertion order - elements maintain order they were added
Map<String, Integer> insertionMap = new LinkedHashMap<>();

// Access order - elements reorder based on access (get operations)
Map<String, Integer> accessMap = new LinkedHashMap<>(16, 0.75f, true);
```

**Use Cases:**
- When insertion order matters
- Building LRU (Least Recently Used) caches
- Maintaining predictable iteration order
- Configuration properties where order matters

---

## LRU Cache

LRU (Least Recently Used) Cache is a caching strategy that removes the least recently used items when cache capacity is exceeded.

**Implementation using LinkedHashMap:**
```java
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;
    
    public LRUCache(int capacity) {
        // accessOrder = true for LRU behavior
        super(capacity + 1, 0.75f, true);
        this.capacity = capacity;
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
    
    // Optional: Override to add custom eviction logic
    @Override
    public V put(K key, V value) {
        return super.put(key, value);
    }
}
```

**Usage Example:**
```java
LRUCache<String, String> cache = new LRUCache<>(3);

cache.put("A", "Apple");
cache.put("B", "Banana");
cache.put("C", "Cherry");

// Cache: A=Apple, B=Banana, C=Cherry

cache.get("A"); // Access A, moves to end
// Cache: B=Banana, C=Cherry, A=Apple

cache.put("D", "Date"); // Exceeds capacity, removes LRU (B)
// Cache: C=Cherry, A=Apple, D=Date
```

**Manual Implementation:**
```java
public class ManualLRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head;
    private final Node<K, V> tail;
    
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;
    }
    
    public ManualLRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node<>();
        this.tail = new Node<>();
        head.next = tail;
        tail.prev = head;
    }
    
    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) return null;
        
        moveToHead(node);
        return node.value;
    }
    
    public void put(K key, V value) {
        Node<K, V> existing = cache.get(key);
        
        if (existing != null) {
            existing.value = value;
            moveToHead(existing);
        } else {
            Node<K, V> newNode = new Node<>();
            newNode.key = key;
            newNode.value = value;
            
            if (cache.size() >= capacity) {
                Node<K, V> tail = removeTail();
                cache.remove(tail.key);
            }
            
            cache.put(key, newNode);
            addToHead(newNode);
        }
    }
    
    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addToHead(node);
    }
    
    // Additional helper methods...
}
```

**Applications:**
- CPU cache systems
- Web browser cache
- Database buffer pools
- Operating system page replacement
- CDN (Content Delivery Networks)

---

## WeakHashMap

WeakHashMap is a Map implementation where keys are held using weak references, allowing them to be garbage collected when no strong references exist.

**Key Characteristics:**
- Keys are weakly referenced
- Entries can be automatically removed by garbage collector
- Prevents memory leaks in certain scenarios
- Not thread-safe
- Iteration behavior may change during GC

**Weak References:**
```java
// Strong reference (normal)
Object strongRef = new Object(); // Object won't be GC'd while strongRef exists

// Weak reference
WeakReference<Object> weakRef = new WeakReference<>(new Object()); // Can be GC'd anytime
```

**Example:**
```java
WeakHashMap<Object, String> weakMap = new WeakHashMap<>();

// Create keys
Object key1 = new Object();
Object key2 = new Object();
Object key3 = new Object();

// Add entries
weakMap.put(key1, "Value 1");
weakMap.put(key2, "Value 2");
weakMap.put(key3, "Value 3");

System.out.println("Size before GC: " + weakMap.size()); // 3

// Remove strong references
key1 = null;
key2 = null;
// key3 still has strong reference

// Force garbage collection
System.gc();
Thread.sleep(1000); // Give GC time to work

System.out.println("Size after GC: " + weakMap.size()); // May be 1 (only key3)
```

**Use Cases:**
```java
// 1. Observer pattern - prevent memory leaks
public class EventManager {
    private WeakHashMap<EventListener, String> listeners = new WeakHashMap<>();
    
    public void addListener(EventListener listener) {
        listeners.put(listener, "");
    }
    
    // Listeners automatically removed when they go out of scope
}

// 2. Caching with automatic cleanup
public class ImageCache {
    private WeakHashMap<String, BufferedImage> cache = new WeakHashMap<>();
    
    public BufferedImage getImage(String filename) {
        return cache.computeIfAbsent(filename, this::loadImage);
    }
    
    // Images automatically removed when not referenced elsewhere
}

// 3. Metadata storage
public class ObjectMetadata {
    private static WeakHashMap<Object, Map<String, Object>> metadata = new WeakHashMap<>();
    
    public static void setProperty(Object obj, String key, Object value) {
        metadata.computeIfAbsent(obj, k -> new HashMap<>()).put(key, value);
    }
    
    // Metadata automatically cleaned when objects are GC'd
}
```

**Important Notes:**
- Don't rely on immediate cleanup after nullifying references
- GC timing is unpredictable
- Useful for preventing memory leaks, not for general caching
- Consider using proper cache libraries for caching needs

---

## Garbage Collection

Garbage Collection (GC) is Java's automatic memory management system that reclaims memory used by objects that are no longer reachable.

**Key Concepts:**

**1. Object Lifecycle:**
```java
// Object creation
String str = new String("Hello"); // Object created in heap

// Object becomes unreachable
str = null; // No references to "Hello" object

// Object eligible for GC
// GC will eventually reclaim memory
```

**2. Types of References:**
```java
// Strong Reference (default) - prevents GC
Object strongRef = new Object();

// Weak Reference - allows GC
WeakReference<Object> weakRef = new WeakReference<>(new Object());

// Soft Reference - GC only when memory pressure
SoftReference<Object> softRef = new SoftReference<>(new Object());

// Phantom Reference - for cleanup actions
PhantomReference<Object> phantomRef = new PhantomReference<>(new Object(), referenceQueue);
```

**3. Memory Areas:**
- **Young Generation:** Where new objects are allocated
  - Eden Space: Initial allocation area
  - Survivor Spaces (S0, S1): Objects that survive initial GC
- **Old Generation (Tenured):** Long-lived objects
- **Metaspace:** Class metadata (replaces PermGen in Java 8+)

**4. GC Algorithms:**
```java
// Serial GC - single threaded
-XX:+UseSerialGC

// Parallel GC - multiple threads (default for server)
-XX:+UseParallelGC

// G1GC - low latency for large heaps
-XX:+UseG1GC

// ZGC - ultra-low latency (Java 11+)
-XX:+UseZGC
```

**5. GC Monitoring:**
```java
// Memory usage
MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();

System.out.println("Used: " + heapUsage.getUsed());
System.out.println("Max: " + heapUsage.getMax());

// GC information
List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
for (GarbageCollectorMXBean gcBean : gcBeans) {
    System.out.println("GC Name: " + gcBean.getName());
    System.out.println("Collections: " + gcBean.getCollectionCount());
    System.out.println("Time: " + gcBean.getCollectionTime() + "ms");
}
```

**6. Memory Leaks Prevention:**
```java
// Avoid static collections growing indefinitely
private static List<Object> staticList = new ArrayList<>(); // Potential leak

// Use weak references for caches
private WeakHashMap<Key, Value> cache = new WeakHashMap<>();

// Close resources properly
try (FileInputStream fis = new FileInputStream("file.txt")) {
    // Use resource
} // Automatically closed

// Remove listeners
eventSource.addListener(listener);
// Don't forget: eventSource.removeListener(listener);
```

**7. GC Tuning Parameters:**
```bash
# Heap size
-Xms2g -Xmx4g

# Young generation size
-XX:NewRatio=3
-XX:NewSize=256m

# GC logging
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps

# G1GC tuning
-XX:MaxGCPauseMillis=200
-XX:G1HeapRegionSize=16m
```

**Best Practices:**
- Avoid creating unnecessary objects in loops
- Use object pools for expensive objects
- Properly size heap based on application needs
- Monitor GC logs for performance tuning
- Use appropriate data structures (primitive collections when possible)

---

## Comparable

Comparable interface defines the natural ordering of objects by implementing the compareTo() method. Classes that implement Comparable can be sorted automatically.

**Key Characteristics:**
- Single abstract method: compareTo(T other)
- Defines natural ordering for a class
- Used by Collections.sort(), Arrays.sort(), TreeSet, TreeMap
- Should be consistent with equals() method

**Method Signature:**
```java
public interface Comparable<T> {
    int compareTo(T o);
}
```

**Return Value Contract:**
- **Negative integer:** this object < specified object
- **Zero:** this object == specified object  
- **Positive integer:** this object > specified object

**Implementation Examples:**
```java
// Simple numeric comparison
public class Student implements Comparable<Student> {
    private String name;
    private int age;
    private double gpa;
    
    @Override
    public int compareTo(Student other) {
        // Natural ordering by GPA (descending)
        return Double.compare(other.gpa, this.gpa);
    }
    
    // Alternative implementations:
    
    // By age (ascending)
    public int compareToByAge(Student other) {
        return Integer.compare(this.age, other.age);
    }
    
    // By name (alphabetical)
    public int compareToByName(Student other) {
        return this.name.compareTo(other.name);
    }
    
    // Multiple criteria
    public int compareToMultiple(Student other) {
        int result = this.name.compareTo(other.name);
        if (result != 0) return result;
        
        result = Integer.compare(this.age, other.age);
        if (result != 0) return result;
        
        return Double.compare(this.gpa, other.gpa);
    }
}

// String comparison example
public class Version implements Comparable<Version> {
    private String version;
    
    @Override
    public int compareTo(Version other) {
        String[] thisParts = this.version.split("\\.");
        String[] otherParts = other.version.split("\\.");
        
        int maxLength = Math.max(thisParts.length, otherParts.length);
        for (int i = 0; i < maxLength; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int otherPart = i < otherParts.length ? Integer.parseInt(otherParts[i]) : 0;
            
            int result = Integer.compare(thisPart, otherPart);
            if (result != 0) return result;
        }
        return 0;
    }
}
```

**Usage Examples:**
```java
List<Student> students = Arrays.asList(
    new Student("Alice", 20, 3.8),
    new Student("Bob", 22, 3.5),
    new Student("Charlie", 19, 3.9)
);

// Automatic sorting using natural ordering
Collections.sort(students); // Sorts by GPA (descending)

// TreeSet automatically sorts
Set<Student> sortedSet = new TreeSet<>(students);

// Arrays sorting
Student[] studentArray = students.toArray(new Student[0]);
Arrays.sort(studentArray);
```

**Consistency with equals():**
```java
public class Person implements Comparable<Person> {
    private String name;
    private int age;
    
    @Override
    public int compareTo(Person other) {
        int result = this.name.compareTo(other.name);
        return result != 0 ? result : Integer.compare(this.age, other.age);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Person person = (Person) obj;
        return age == person.age && Objects.equals(name, person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

**Best Practices:**
- Always be consistent with equals()
- Handle null values appropriately  
- Use existing compareTo() methods when possible (String, Integer, etc.)
- Consider using Comparator for external sorting logic
- Document the natural ordering clearly

**Comparable vs Comparator:**
- **Comparable:** Natural ordering defined within the class
- **Comparator:** External sorting logic, multiple sorting strategies possible

---

## SortedMap

SortedMap is an interface that extends Map and maintains its entries in sorted order based on keys' natural ordering or a provided Comparator.

**Key Characteristics:**
- Keys are maintained in sorted order
- Either natural ordering (Comparable) or custom Comparator
- Provides range-view operations
- TreeMap is the primary implementation
- No null keys (generally)

**Additional Methods:**
```java
// Range operations
SortedMap<K,V> subMap(K fromKey, K toKey)     // Elements from fromKey (inclusive) to toKey (exclusive)
SortedMap<K,V> headMap(K toKey)               // Elements less than toKey
SortedMap<K,V> tailMap(K fromKey)             // Elements greater than or equal to fromKey

// Boundary operations
K firstKey()                                  // Lowest key
K lastKey()                                   // Highest key

// Comparator access
Comparator<? super K> comparator()            // Returns comparator used, null for natural ordering
```

**Example:**
```java
// Natural ordering (String implements Comparable)
SortedMap<String, Integer> naturalOrder = new TreeMap<>();
naturalOrder.put("zebra", 1);
naturalOrder.put("apple", 2);
naturalOrder.put("banana", 3);

// Iteration order: apple, banana, zebra
for (String key : naturalOrder.keySet()) {
    System.out.println(key + " = " + naturalOrder.get(key));
}

// Custom ordering
SortedMap<String, Integer> reverseOrder = new TreeMap<>(Collections.reverseOrder());
reverseOrder.put("apple", 1);
reverseOrder.put("banana", 2);
reverseOrder.put("zebra", 3);

// Iteration order: zebra, banana, apple

// Range operations
SortedMap<String, Integer> subMap = naturalOrder.subMap("apple", "zebra");
// Contains: apple, banana (zebra excluded)

SortedMap<String, Integer> headMap = naturalOrder.headMap("banana");
// Contains: apple

SortedMap<String, Integer> tailMap = naturalOrder.tailMap("banana");
// Contains: banana, zebra

// Boundary operations
String first = naturalOrder.firstKey(); // "apple"
String last = naturalOrder.lastKey();   // "zebra"
```

**Use Cases:**
- Maintaining sorted dictionaries
- Range queries on ordered data
- Phonebook/directory applications
- Time-series data with timestamp keys
- Configuration properties that need ordering

**Performance:**
- TreeMap: O(log n) for basic operations
- Maintains sorted order automatically
- Range operations are efficient

---

## NavigableMap

NavigableMap extends SortedMap and provides navigation methods for finding closest matches to given search targets.

**Key Characteristics:**
- All SortedMap capabilities plus navigation methods
- Find closest matches (ceiling, floor, higher, lower)
- Reverse navigation support
- TreeMap is the standard implementation
- Null keys not permitted

**Navigation Methods:**
```java
// Closest match methods
Map.Entry<K,V> ceilingEntry(K key)    // Least key >= given key
K ceilingKey(K key)                   // Least key >= given key
Map.Entry<K,V> floorEntry(K key)      // Greatest key <= given key  
K floorKey(K key)                     // Greatest key <= given key
Map.Entry<K,V> higherEntry(K key)     // Least key > given key
K higherKey(K key)                    // Least key > given key
Map.Entry<K,V> lowerEntry(K key)      // Greatest key < given key
K lowerKey(K key)                     // Greatest key < given key

// Boundary with removal
Map.Entry<K,V> pollFirstEntry()       // Remove and return lowest entry
Map.Entry<K,V> pollLastEntry()        // Remove and return highest entry

// Reverse views
NavigableSet<K> descendingKeySet()    // Reverse order key set
NavigableMap<K,V> descendingMap()     // Reverse order map

// Enhanced range operations
NavigableMap<K,V> subMap(K fromKey, boolean fromInclusive, 
                        K toKey, boolean toInclusive)
NavigableMap<K,V> headMap(K toKey, boolean inclusive)
NavigableMap<K,V> tailMap(K fromKey, boolean inclusive)
```

**Example:**
```java
NavigableMap<Integer, String> navMap = new TreeMap<>();
navMap.put(1, "one");
navMap.put(3, "three");
navMap.put(5, "five");
navMap.put(7, "seven");
navMap.put(9, "nine");

// Navigation methods
String ceiling4 = navMap.ceilingEntry(4).getValue();  // "five" (5 >= 4)
String floor4 = navMap.floorEntry(4).getValue();      // "three" (3 <= 4)
String higher3 = navMap.higherEntry(3).getValue();    // "five" (5 > 3)
String lower5 = navMap.lowerEntry(5).getValue();      // "three" (3 < 5)

// Exact matches return the entry
String ceiling3 = navMap.ceilingEntry(3).getValue();  // "three" (exact match)

// Boundary operations with removal
Map.Entry<Integer, String> first = navMap.pollFirstEntry(); // Removes (1,"one")
Map.Entry<Integer, String> last = navMap.pollLastEntry();   // Removes (9,"nine")

// Reverse navigation
NavigableMap<Integer, String> descendingMap = navMap.descendingMap();
for (Integer key : descendingMap.keySet()) {
    System.out.println(key); // Prints: 7, 5, 3 (reverse order)
}

// Enhanced range operations
NavigableMap<Integer, String> subMap = navMap.subMap(3, true, 7, false);
// Contains: 3, 5 (3 inclusive, 7 exclusive)
```

**Real-world Example - Time-based Data:**
```java
public class TimeSeriesData {
    private NavigableMap<LocalDateTime, Double> data = new TreeMap<>();
    
    public void addDataPoint(LocalDateTime timestamp, Double value) {
        data.put(timestamp, value);
    }
    
    public Double getValueAt(LocalDateTime timestamp) {
        return data.get(timestamp);
    }
    
    public Double getClosestValue(LocalDateTime timestamp) {
        Map.Entry<LocalDateTime, Double> entry = data.floorEntry(timestamp);
        return entry != null ? entry.getValue() : null;
    }
    
    public Double getNextValue(LocalDateTime timestamp) {
        Map.Entry<LocalDateTime, Double> entry = data.higherEntry(timestamp);
        return entry != null ? entry.getValue() : null;
    }
    
    public NavigableMap<LocalDateTime, Double> getDataInRange(
            LocalDateTime start, LocalDateTime end) {
        return data.subMap(start, true, end, true);
    }
    
    public Double getLatestValue() {
        Map.Entry<LocalDateTime, Double> entry = data.lastEntry();
        return entry != null ? entry.getValue() : null;
    }
}
```

**Use Cases:**
- Time-series databases
- Finding closest matches in sorted data
- Range queries with flexible boundaries
- Navigation in ordered datasets
- Building database-like operations on sorted collections

---

## HashTable

Hashtable is a legacy synchronized implementation of the Map interface, similar to HashMap but with built-in thread safety.

**Key Characteristics:**
- Synchronized (thread-safe)
- Legacy class from Java 1.0
- Does not allow null keys or values
- Uses Enumeration interface (legacy)
- Slower performance due to synchronization overhead

**Differences from HashMap:**
| Feature | Hashtable | HashMap |
|---------|-----------|---------|
| Thread Safety | Synchronized | Not synchronized |
| Null Values | Not allowed | Allowed |
| Null Keys | Not allowed | One allowed |
| Inheritance | Extends Dictionary | Extends AbstractMap |
| Iteration | Fail-fast + Enumeration | Fail-fast |
| Performance | Slower | Faster |

**Example:**
```java
Hashtable<String, Integer> hashtable = new Hashtable<>();

// Basic operations
hashtable.put("apple", 100);
hashtable.put("banana", 80);
// hashtable.put(null, 50);      // Throws NullPointerException
// hashtable.put("grape", null); // Throws NullPointerException

// Thread-safe operations
Integer value = hashtable.get("apple");
hashtable.remove("banana");

// Legacy enumeration (avoid in new code)
Enumeration<String> keys = hashtable.keys();
while (keys.hasMoreElements()) {
    String key = keys.nextElement();
    System.out.println(key + " = " + hashtable.get(key));
}

// Modern iteration (preferred)
for (Map.Entry<String, Integer> entry : hashtable.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
```

**Thread Safety Example:**
```java
Hashtable<String, Integer> table = new Hashtable<>();

// Multiple threads can safely access
Runnable task1 = () -> {
    for (int i = 0; i < 100; i++) {
        table.put("key" + i, i);
    }
};

Runnable task2 = () -> {
    for (int i = 100; i < 200; i++) {
        table.put("key" + i, i);
    }
};

Thread t1 = new Thread(task1);
Thread t2 = new Thread(task2);
t1.start();
t2.start();
```

**Modern Alternatives:**
```java
// Instead of Hashtable, use:

// 1. Collections.synchronizedMap()
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// 2. ConcurrentHashMap (preferred)
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

// 3. For read-heavy scenarios
CopyOnWriteMap<String, Integer> cowMap = new CopyOnWriteMap<>();
```

**When NOT to Use Hashtable:**
- New applications (use ConcurrentHashMap)
- Need null keys/values
- Performance-critical applications
- When fine-grained locking is needed

**Legacy Support:**
- Still used in some legacy systems
- Properties class extends Hashtable
- Some older APIs expect Hashtable

---

## ConcurrentHashMap

ConcurrentHashMap is a thread-safe implementation of Map that provides better concurrency than Hashtable through advanced locking mechanisms.

**Key Characteristics:**
- Thread-safe without synchronizing the entire map
- Better performance than Hashtable in concurrent scenarios
- Segment-based locking (Java 7) / CAS operations (Java 8+)
- No null keys or values allowed
- Fail-safe iterators (don't throw ConcurrentModificationException)

**Evolution:**
- **Java 7:** Segment-based locking (16 segments by default)
- **Java 8+:** CAS (Compare-And-Swap) operations, less locking

**Example:**
```java
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

// Thread-safe operations
concurrentMap.put("apple", 100);
concurrentMap.put("banana", 80);

// Atomic operations
concurrentMap.putIfAbsent("grape", 90); // Only puts if key doesn't exist
Integer oldValue = concurrentMap.replace("apple", 110); // Returns old value

// Conditional operations
boolean replaced = concurrentMap.replace("apple", 110, 120); // Replace only if current value is 110
boolean removed = concurrentMap.remove("banana", 80); // Remove only if value matches

// Compute operations (atomic)
concurrentMap.compute("apple", (key, value) -> value == null ? 1 : value + 1);
concurrentMap.computeIfAbsent("orange", key -> 95);
concurrentMap.computeIfPresent("apple", (key, value) -> value + 5);

// Merge operation
concurrentMap.merge("apple", 10, Integer::sum); // Add 10 to existing value
```

**Concurrent Access Example:**
```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Multiple threads can safely access
ExecutorService executor = Executors.newFixedThreadPool(10);

// Producer threads
for (int i = 0; i < 5; i++) {
    final int threadId = i;
    executor.submit(() -> {
        for (int j = 0; j < 1000; j++) {
            map.put("key-" + threadId + "-" + j, j);
        }
    });
}

// Consumer threads
for (int i = 0; i < 3; i++) {
    executor.submit(() -> {
        map.forEach((key, value) -> {
            // Safe iteration even during concurrent modifications
            System.out.println(key + " = " + value);
        });
    });
}

executor.shutdown();
```

**Bulk Operations (Java 8+):**
```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("a", 1);
map.put("b", 2);
map.put("c", 3);

// Parallel bulk operations
long threshold = 1; // Parallelism threshold

// forEach variants
map.forEach(threshold, (key, value) -> System.out.println(key + "=" + value));
map.forEachKey(threshold, key -> System.out.println("Key: " + key));
map.forEachValue(threshold, value -> System.out.println("Value: " + value));

// Search operations
String result = map.search(threshold, (key, value) -> value > 2 ? key : null);

// Reduce operations
Integer sum = map.reduce(threshold, (key, value) -> value, 0, Integer::sum);
```

**Performance Comparison:**
```java
// Benchmark example (conceptual)
Map<String, Integer> hashtable = new Hashtable<>();
Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

// Under high concurrency:
// ConcurrentHashMap > Collections.synchronizedMap > Hashtable (performance)
```

**Best Practices:**
- Prefer ConcurrentHashMap over Hashtable and Collections.synchronizedMap()
- Use atomic operations (computeIfAbsent, merge) instead of check-then-act patterns
- Consider parallelism threshold for bulk operations
- Size constructor appropriately for expected load

**Use Cases:**
- High-concurrency applications
- Caching systems
- Shared data structures between threads
- Producer-consumer scenarios
- Real-time systems requiring thread-safe maps

---

## ConcurrentSkipListMap

ConcurrentSkipListMap is a thread-safe, sorted map implementation based on skip list data structure, providing O(log n) operations with high concurrency.

**Key Characteristics:**
- Thread-safe and sorted simultaneously
- Based on skip list data structure
- O(log n) time complexity for basic operations
- No locking for most read operations
- Implements NavigableMap interface
- No null keys or values

**Skip List Structure:**
A skip list is a probabilistic data structure that maintains multiple levels of linked lists, allowing for fast search, insertion, and deletion operations.

**Example:**
```java
ConcurrentSkipListMap<Integer, String> skipListMap = new ConcurrentSkipListMap<>();

// Thread-safe insertion (maintains sorted order)
skipListMap.put(3, "three");
skipListMap.put(1, "one");
skipListMap.put(5, "five");
skipListMap.put(2, "two");
skipListMap.put(4, "four");

// Iteration is in sorted order: 1, 2, 3, 4, 5
skipListMap.forEach((key, value) -> System.out.println(key + " = " + value));

// NavigableMap operations (thread-safe)
String ceiling = skipListMap.ceilingEntry(3).getValue(); // "three"
String floor = skipListMap.floorEntry(3).getValue();     // "three"
String higher = skipListMap.higherEntry(3).getValue();   // "four"
String lower = skipListMap.lowerEntry(3).getValue();     // "two"

// Range operations
ConcurrentNavigableMap<Integer, String> subMap = skipListMap.subMap(2, true, 4, true);
// Contains: 2, 3, 4

// Reverse navigation
ConcurrentNavigableMap<Integer, String> descendingMap = skipListMap.descendingMap();
```

**Concurrent Access Example:**
```java
ConcurrentSkipListMap<LocalDateTime, String> eventLog = new ConcurrentSkipListMap<>();

// Multiple threads can safely add events
ExecutorService executor = Executors.newFixedThreadPool(5);

for (int i = 0; i < 5; i++) {
    final int threadId = i;
    executor.submit(() -> {
        for (int j = 0; j < 100; j++) {
            LocalDateTime timestamp = LocalDateTime.now().plusSeconds(threadId * 100 + j);
            eventLog.put(timestamp, "Event from thread " + threadId + ", iteration " + j);
        }
    });
}

// Reader thread can safely iterate even during concurrent modifications
executor.submit(() -> {
    while (true) {
        eventLog.forEach((timestamp, event) -> {
            System.out.println(timestamp + ": " + event);
        });
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            break;
        }
    }
});

executor.shutdown();
```

**Comparison with Other Concurrent Maps:**

| Feature | ConcurrentHashMap | ConcurrentSkipListMap | TreeMap |
|---------|-------------------|----------------------|---------|
| Thread Safety | Yes | Yes | No |
| Ordering | No | Yes (sorted) | Yes (sorted) |
| Performance | O(1) average | O(log n) | O(log n) |
| Null Keys | No | No | No (with Comparator) |
| NavigableMap | No | Yes | Yes |

**Real-world Use Case - Time-series Database:**
```java
public class TimeSeriesDatabase {
    private final ConcurrentSkipListMap<Long, Double> data = new ConcurrentSkipListMap<>();
    
    public void addDataPoint(long timestamp, double value) {
        data.put(timestamp, value);
    }
    
    public Double getValueAt(long timestamp) {
        return data.get(timestamp);
    }
    
    public Double getLastValueBefore(long timestamp) {
        Map.Entry<Long, Double> entry = data.floorEntry(timestamp);
        return entry != null ? entry.getValue() : null;
    }
    
    public ConcurrentNavigableMap<Long, Double> getRangeData(long startTime, long endTime) {
        return data.subMap(startTime, true, endTime, true);
    }
    
    public void removeOldData(long cutoffTime) {
        // Efficiently remove old entries
        ConcurrentNavigableMap<Long, Double> oldData = data.headMap(cutoffTime, true);
        oldData.clear();
    }
    
    // Thread-safe aggregation
    public double getAverage(long startTime, long endTime) {
        ConcurrentNavigableMap<Long, Double> rangeData = getRangeData(startTime, endTime);
        return rangeData.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
}
```

**Use Cases:**
- Time-series databases
- Event logging systems
- Priority queues with concurrent access
- Sorted caches
- Real-time analytics
- Leaderboards and rankings

**Performance Considerations:**
- Better concurrent read performance than synchronized TreeMap
- Write operations may be slower than ConcurrentHashMap
- Memory overhead due to skip list structure
- Good balance between concurrency and ordering requirements

---

## EnumMap

EnumMap is a specialized Map implementation designed exclusively for enum keys, providing exceptional performance and type safety.

**Key Characteristics:**
- Keys must be from a single enum type
- Extremely fast operations (array-based internally)
- Maintains natural enum order (declaration order)
- Type-safe at compile time
- Space-efficient
- Not thread-safe

**Internal Implementation:**
EnumMap uses an array internally where enum ordinal values serve as indices, making operations extremely fast.

**Example:**
```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

public enum Priority {
    LOW, MEDIUM, HIGH, CRITICAL
}

// Basic usage
EnumMap<Day, String> schedule = new EnumMap<>(Day.class);
schedule.put(Day.MONDAY, "Team Meeting");
schedule.put(Day.TUESDAY, "Code Review");
schedule.put(Day.WEDNESDAY, "Project Planning");
schedule.put(Day.FRIDAY, "Team Lunch");

// Iteration maintains enum declaration order
for (Day day : schedule.keySet()) {
    System.out.println(day + ": " + schedule.get(day));
}
// Output: MONDAY: Team Meeting, TUESDAY: Code Review, etc.

// All enum values can be quickly checked
for (Day day : Day.values()) {
    String activity = schedule.get(day);
    if (activity != null) {
        System.out.println(day + " has activity: " + activity);
    } else {
        System.out.println(day + " is free");
    }
}
```

**Complex Example - Task Management:**
```java
public class TaskManager {
    public enum TaskStatus {
        PENDING, IN_PROGRESS, TESTING, COMPLETED, CANCELLED
    }
    
    public enum TaskPriority {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    
    private EnumMap<TaskStatus, List<Task>> tasksByStatus;
    private EnumMap<TaskPriority, Integer> taskCountByPriority;
    
    public TaskManager() {
        tasksByStatus = new EnumMap<>(TaskStatus.class);
        taskCountByPriority = new EnumMap<>(TaskPriority.class);
        
        // Initialize all enum values
        for (TaskStatus status : TaskStatus.values()) {
            tasksByStatus.put(status, new ArrayList<>());
        }
        
        for (TaskPriority priority : TaskPriority.values()) {
            taskCountByPriority.put(priority, 0);
        }
    }
    
    public void addTask(Task task) {
        TaskStatus status = task.getStatus();
        TaskPriority priority = task.getPriority();
        
        tasksByStatus.get(status).add(task);
        taskCountByPriority.put(priority, taskCountByPriority.get(priority) + 1);
    }
    
    public List<Task> getTasksByStatus(TaskStatus status) {
        return new ArrayList<>(tasksByStatus.get(status));
    }
    
    public void printStatusSummary() {
        for (TaskStatus status : TaskStatus.values()) {
            int count = tasksByStatus.get(status).size();
            System.out.println(status + ": " + count + " tasks");
        }
    }
    
    public void printPrioritySummary() {
        for (TaskPriority priority : TaskPriority.values()) {
            int count = taskCountByPriority.get(priority);
            System.out.println(priority + ": " + count + " tasks");
        }
    }
}
```

**Performance Comparison:**
```java
// Performance test (conceptual)
public void performanceComparison() {
    EnumMap<Day, String> enumMap = new EnumMap<>(Day.class);
    HashMap<Day, String> hashMap = new HashMap<>();
    
    // EnumMap operations are faster due to:
    // 1. Array-based implementation (O(1) with no hashing)
    // 2. No hash computation needed
    // 3. Better memory locality
    // 4. No collision handling
    
    // Benchmark results typically show:
    // EnumMap: ~2-3x faster for get/put operations
    // EnumMap: ~50% less memory usage
}
```

**Advanced Usage - State Machine:**
```java
public class OrderStateMachine {
    public enum OrderState {
        CREATED, PAID, SHIPPED, DELIVERED, CANCELLED, REFUNDED
    }
    
    // Define valid state transitions
    private static final EnumMap<OrderState, Set<OrderState>> VALID_TRANSITIONS;
    
    static {
        VALID_TRANSITIONS = new EnumMap<>(OrderState.class);
        
        VALID_TRANSITIONS.put(OrderState.CREATED, 
            EnumSet.of(OrderState.PAID, OrderState.CANCELLED));
        VALID_TRANSITIONS.put(OrderState.PAID, 
            EnumSet.of(OrderState.SHIPPED, OrderState.REFUNDED));
        VALID_TRANSITIONS.put(OrderState.SHIPPED, 
            EnumSet.of(OrderState.DELIVERED));
        VALID_TRANSITIONS.put(OrderState.DELIVERED, 
            EnumSet.of(OrderState.REFUNDED));
        VALID_TRANSITIONS.put(OrderState.CANCELLED, 
            EnumSet.noneOf(OrderState.class));
        VALID_TRANSITIONS.put(OrderState.REFUNDED, 
            EnumSet.noneOf(OrderState.class));
    }
    
    public boolean canTransition(OrderState from, OrderState to) {
        return VALID_TRANSITIONS.get(from).contains(to);
    }
    
    public Set<OrderState> getValidNextStates(OrderState currentState) {
        return VALID_TRANSITIONS.get(currentState);
    }
}
```

**Best Practices:**
- Always prefer EnumMap when keys are enums
- Initialize with all enum values for complete coverage
- Use EnumSet for enum value collections
- Combine with other enum-based collections for maximum efficiency

**Use Cases:**
- Configuration mappings
- State machines
- Feature flags
- Status tracking
- Category-based data organization
- Performance-critical enum-keyed lookups

---

## ImmutableMap

ImmutableMap represents a Map that cannot be modified after creation. Once constructed, its contents cannot be changed, providing thread safety and preventing accidental modifications.

**Key Characteristics:**
- Cannot be modified after creation
- Thread-safe by design
- Memory efficient for read-only data
- Fast iteration and lookup
- Available in Guava library and Java 9+ (Map.of())

**Java 9+ Built-in Support:**
```java
// Java 9+ Map.of() methods
Map<String, Integer> immutableMap1 = Map.of();  // Empty map

Map<String, Integer> immutableMap2 = Map.of(
    "apple", 100,
    "banana", 80
);

Map<String, Integer> immutableMap3 = Map.of(
    "apple", 100,
    "banana", 80,
    "orange", 120,
    "grape", 90
);

// Up to 10 key-value pairs
Map<String, Integer> immutableMap4 = Map.of(
    "k1", 1, "k2", 2, "k3", 3, "k4", 4, "k5", 5,
    "k6", 6, "k7", 7, "k8", 8, "k9", 9, "k10", 10
);

// For more than 10 pairs, use Map.ofEntries()
Map<String, Integer> immutableMap5 = Map.ofEntries(
    Map.entry("apple", 100),
    Map.entry("banana", 80),
    Map.entry("orange", 120)
    // ... more entries
);
```

**Google Guava ImmutableMap:**
```java
// Guava ImmutableMap
import com.google.common.collect.ImmutableMap;

// Builder pattern
ImmutableMap<String, Integer> map1 = ImmutableMap.<String, Integer>builder()
    .put("apple", 100)
    .put("banana", 80)
    .put("orange", 120)
    .build();

// Direct construction
ImmutableMap<String, Integer> map2 = ImmutableMap.of(
    "apple", 100,
    "banana", 80,
    "orange", 120
);

// From existing map
Map<String, Integer> mutableMap = new HashMap<>();
mutableMap.put("apple", 100);
mutableMap.put("banana", 80);

ImmutableMap<String, Integer> map3 = ImmutableMap.copyOf(mutableMap);

// Null handling (throws exception)
try {
    ImmutableMap.of("key", null); // Throws NullPointerException
} catch (NullPointerException e) {
    System.out.println("Null values not allowed");
}
```

**Creating from Collections:**
```java
// From stream (Java 8+)
List<String> fruits = Arrays.asList("apple", "banana", "orange");

Map<String, Integer> lengthMap = fruits.stream()
    .collect(Collectors.toMap(
        Function.identity(),
        String::length
    ));

// Convert to immutable
Map<String, Integer> immutableLengthMap = Map.copyOf(lengthMap); // Java 10+

// Or using Guava
ImmutableMap<String, Integer> guavaImmutable = ImmutableMap.copyOf(lengthMap);
```

**Thread Safety Example:**
```java
// Shared immutable map between threads
public class ConfigurationManager {
    private final Map<String, String> config;
    
    public ConfigurationManager(Map<String, String> initialConfig) {
        // Create immutable copy
        this.config = Map.copyOf(initialConfig);
    }
    
    public String getConfigValue(String key) {
        return config.get(key); // Thread-safe, no synchronization needed
    }
    
    public Map<String, String> getAllConfig() {
        return config; // Safe to return, cannot be modified
    }
    
    // To update configuration, create new instance
    public ConfigurationManager withUpdatedConfig(String key, String value) {
        Map<String, String> newConfig = new HashMap<>(this.config);
        newConfig.put(key, value);
        return new ConfigurationManager(newConfig);
    }
}
```

**Performance Comparison:**
```java
// Performance characteristics
public void performanceComparison() {
    // Memory usage
    Map<String, Integer> hashMap = new HashMap<>();
    ImmutableMap<String, Integer> immutableMap = ImmutableMap.of("key", 1);
    
    // ImmutableMap typically uses less memory:
    // - No need for modification-related overhead
    // - Optimized internal structure
    // - Better cache locality for reads
    
    // Read performance: ImmutableMap >= HashMap
    // Write performance: N/A (immutable)
    // Thread contention: ImmutableMap wins (no locks needed)
}
```

**Functional Programming Integration:**
```java
public class OrderProcessor {
    private final ImmutableMap<OrderStatus, OrderHandler> handlers;
    
    public OrderProcessor() {
        this.handlers = ImmutableMap.<OrderStatus, OrderHandler>builder()
            .put(OrderStatus.CREATED, this::handleCreated)
            .put(OrderStatus.PAID, this::handlePaid)
            .put(OrderStatus.SHIPPED, this::handleShipped)
            .build();
    }
    
    public void processOrder(Order order) {
        OrderHandler handler = handlers.get(order.getStatus());
        if (handler != null) {
            handler.handle(order);
        }
    }
    
    private void handleCreated(Order order) { /* implementation */ }
    private void handlePaid(Order order) { /* implementation */ }
    private void handleShipped(Order order) { /* implementation */ }
    
    @FunctionalInterface
    interface OrderHandler {
        void handle(Order order);
    }
}
```

**Immutable Collections Ecosystem:**
```java
// Building complex immutable data structures
public class UserProfile {
    private final String userId;
    private final ImmutableMap<String, String> personalInfo;
    private final ImmutableSet<String> permissions;
    private final ImmutableList<String> activityLog;
    
    private UserProfile(Builder builder) {
        this.userId = builder.userId;
        this.personalInfo = ImmutableMap.copyOf(builder.personalInfo);
        this.permissions = ImmutableSet.copyOf(builder.permissions);
        this.activityLog = ImmutableList.copyOf(builder.activityLog);
    }
    
    // Builder pattern for construction
    public static class Builder {
        private String userId;
        private Map<String, String> personalInfo = new HashMap<>();
        private Set<String> permissions = new HashSet<>();
        private List<String> activityLog = new ArrayList<>();
        
        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }
        
        public Builder addPersonalInfo(String key, String value) {
            this.personalInfo.put(key, value);
            return this;
        }
        
        public Builder addPermission(String permission) {
            this.permissions.add(permission);
            return this;
        }
        
        public UserProfile build() {
            return new UserProfile(this);
        }
    }
}
```

**Use Cases:**
- Configuration objects
- Lookup tables and mappings
- Constants and enumerations
- Caching read-only data
- Thread-safe shared data
- Functional programming
- API responses that shouldn't change
- Value objects in domain modeling

**Advantages:**
- Thread safety without synchronization
- Prevention of accidental modifications
- Better performance for read-heavy scenarios
- Memory efficiency
- Clear intent (immutability)

**Limitations:**
- Cannot be modified (need new instance for changes)
- Initial construction overhead
- Not suitable for frequently changing data

---

## Set

Set is a Collection interface that represents a mathematical set - a collection containing no duplicate elements. It models the mathematical set abstraction.

**Key Characteristics:**
- No duplicate elements allowed
- At most one null element (if nulls are permitted)
- Mathematical set
