# ConcurrentSkipListMap

## What is it?

Thread-safe, **sorted** map based on **skip list** data structure. Provides **O(log n)** performance for most operations.

**Core Concept**: A probabilistic data structure with multiple levels of linked lists that allows fast search, insert, and delete while maintaining sorted order. Thread-safe alternative to TreeMap.

## Key Features

- **Thread-safe**: Multiple threads can read/write concurrently
- **Sorted**: Keys always sorted (natural order or comparator)
- **Lock-free**: Uses CAS (Compare-And-Swap) operations
- **O(log n) operations**: get, put, remove, containsKey
- **No null keys**: Cannot use null as key
- **Navigable**: Supports range queries, floor, ceiling operations
- **Scalable**: Better than TreeMap for concurrent access

## Basic Example

```java
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapExample {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        
        // Add elements (automatically sorted by key)
        map.put(3, "Three");
        map.put(1, "One");
        map.put(5, "Five");
        map.put(2, "Two");
        map.put(4, "Four");
        
        // Iterate - always in sorted order
        map.forEach((k, v) -> System.out.println(k + " = " + v));
        
        // Output (sorted by key):
        // 1 = One
        // 2 = Two
        // 3 = Three
        // 4 = Four
        // 5 = Five
    }
}
```

## Common Operations

```java
ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

// Put
map.put(1, "One");
map.putIfAbsent(2, "Two");  // Only if key doesn't exist

// Get
String value = map.get(1);
String defaultVal = map.getOrDefault(10, "Default");

// Remove
String removed = map.remove(1);

// Contains
boolean hasKey = map.containsKey(1);
boolean hasValue = map.containsValue("One");

// Size
int size = map.size();
boolean empty = map.isEmpty();

// Clear
map.clear();
```

## Navigable Operations

```java
ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
map.put(10, "Ten");
map.put(20, "Twenty");
map.put(30, "Thirty");
map.put(40, "Forty");

// First and Last
Integer firstKey = map.firstKey();      // 10
Integer lastKey = map.lastKey();        // 40
Map.Entry<Integer, String> firstEntry = map.firstEntry();
Map.Entry<Integer, String> lastEntry = map.lastEntry();

// Floor (<=) and Ceiling (>=)
Integer floor25 = map.floorKey(25);     // 20 (largest <= 25)
Integer ceiling25 = map.ceilingKey(25); // 30 (smallest >= 25)

// Lower (<) and Higher (>)
Integer lower30 = map.lowerKey(30);     // 20 (largest < 30)
Integer higher30 = map.higherKey(30);   // 40 (smallest > 30)

// Poll (remove and return)
Map.Entry<Integer, String> first = map.pollFirstEntry();  // Remove first
Map.Entry<Integer, String> last = map.pollLastEntry();    // Remove last
```

## Range Views (SubMaps)

```java
ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
map.put(1, "One");
map.put(2, "Two");
map.put(3, "Three");
map.put(4, "Four");
map.put(5, "Five");

// SubMap (inclusive, exclusive)
NavigableMap<Integer, String> subMap = map.subMap(2, 4);
// Contains: {2=Two, 3=Three}

// HeadMap (less than)
NavigableMap<Integer, String> headMap = map.headMap(3);
// Contains: {1=One, 2=Two}

// TailMap (greater than or equal)
NavigableMap<Integer, String> tailMap = map.tailMap(3);
// Contains: {3=Three, 4=Four, 5=Five}

// Descending view
NavigableMap<Integer, String> descending = map.descendingMap();
// Reverse order iteration
```

## Real-World Example: Leaderboard System

```java
class Player {
    private String name;
    private int score;
    
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
    
    public String getName() { return name; }
    public int getScore() { return score; }
}

public class Leaderboard {
    // Score -> Player (sorted by score descending)
    private ConcurrentSkipListMap<Integer, Player> scoreMap;
    
    public Leaderboard() {
        // Reverse order comparator (highest score first)
        scoreMap = new ConcurrentSkipListMap<>((a, b) -> Integer.compare(b, a));
    }
    
    public void updateScore(String playerName, int newScore) {
        // Remove old score if exists
        scoreMap.values().removeIf(p -> p.getName().equals(playerName));
        // Add with new score
        scoreMap.put(newScore, new Player(playerName, newScore));
        System.out.println(playerName + " scored " + newScore);
    }
    
    public void printTopN(int n) {
        System.out.println("\nTop " + n + " Players:");
        scoreMap.entrySet().stream()
            .limit(n)
            .forEach(entry -> System.out.println(
                entry.getValue().getName() + ": " + entry.getKey()
            ));
    }
    
    public int getRank(String playerName) {
        int rank = 1;
        for (Player player : scoreMap.values()) {
            if (player.getName().equals(playerName)) {
                return rank;
            }
            rank++;
        }
        return -1;
    }
    
    public static void main(String[] args) throws InterruptedException {
        Leaderboard leaderboard = new Leaderboard();
        
        // Multiple players updating scores concurrently
        Thread t1 = new Thread(() -> {
            leaderboard.updateScore("Alice", 1500);
            leaderboard.updateScore("Alice", 1800);  // Update
        });
        
        Thread t2 = new Thread(() -> {
            leaderboard.updateScore("Bob", 2000);
            leaderboard.updateScore("Charlie", 1200);
        });
        
        Thread t3 = new Thread(() -> {
            leaderboard.updateScore("Diana", 1700);
            leaderboard.updateScore("Eve", 1900);
        });
        
        t1.start();
        t2.start();
        t3.start();
        
        t1.join();
        t2.join();
        t3.join();
        
        leaderboard.printTopN(3);
        System.out.println("\nAlice's rank: " + leaderboard.getRank("Alice"));
    }
}
```

## Real-World Example: Event Timeline

```java
class Event {
    private String description;
    private long timestamp;
    
    public Event(String description) {
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getDescription() { return description; }
    public long getTimestamp() { return timestamp; }
}

public class EventTimeline {
    private ConcurrentSkipListMap<Long, Event> timeline = new ConcurrentSkipListMap<>();
    
    public void addEvent(String description) {
        Event event = new Event(description);
        timeline.put(event.getTimestamp(), event);
        System.out.println("Event added: " + description);
    }
    
    public void getEventsInRange(long startTime, long endTime) {
        System.out.println("\nEvents from " + startTime + " to " + endTime + ":");
        timeline.subMap(startTime, endTime).forEach((time, event) -> 
            System.out.println(time + ": " + event.getDescription())
        );
    }
    
    public void getRecentEvents(int count) {
        System.out.println("\nLast " + count + " events:");
        timeline.descendingMap().entrySet().stream()
            .limit(count)
            .forEach(entry -> System.out.println(
                entry.getKey() + ": " + entry.getValue().getDescription()
            ));
    }
    
    public void cleanupOldEvents(long beforeTime) {
        NavigableMap<Long, Event> oldEvents = timeline.headMap(beforeTime);
        int removed = oldEvents.size();
        oldEvents.clear();
        System.out.println("Removed " + removed + " old events");
    }
    
    public static void main(String[] args) throws InterruptedException {
        EventTimeline timeline = new EventTimeline();
        
        timeline.addEvent("User login");
        Thread.sleep(100);
        timeline.addEvent("File uploaded");
        Thread.sleep(100);
        timeline.addEvent("Payment processed");
        Thread.sleep(100);
        timeline.addEvent("Email sent");
        
        timeline.getRecentEvents(2);
    }
}
```

## ConcurrentSkipListMap vs Others

| Feature | ConcurrentSkipListMap | ConcurrentHashMap | TreeMap |
|---------|----------------------|-------------------|---------|
| Thread-safe | Yes | Yes | No |
| Sorted | Yes | No | Yes |
| Locking | Lock-free (CAS) | Lock-free (CAS) | External sync needed |
| Performance | O(log n) | O(1) average | O(log n) |
| Null keys | No | No | No (with comparator) |
| Navigable | Yes | No | Yes |
| Best for | Sorted concurrent | Unsorted concurrent | Sorted single-thread |

## When to Use

**Use ConcurrentSkipListMap when:**
- Need sorted map with concurrent access
- Range queries on sorted data
- Floor, ceiling, higher, lower operations
- Leaderboards, rankings, timelines
- Event ordering by timestamp
- Priority-based processing with concurrent updates

**Avoid when:**
- Don't need sorted order (use ConcurrentHashMap - faster)
- Single-threaded (use TreeMap - simpler)
- Need null keys
- Performance-critical and order doesn't matter

## How Skip List Works

```
Skip List Structure (4 levels):

Level 3:  1 -----------------------> 9
Level 2:  1 --------> 5 -----------> 9
Level 1:  1 --> 3 --> 5 --> 7 -----> 9
Level 0:  1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9

Search for 7:
1. Start at top-left (level 3, node 1)
2. Move right while value < 7
3. Drop down when can't move right
4. Repeat until found

Average height: O(log n)
Average search: O(log n)
```

**Why Skip List?**
- Simpler than balanced trees (AVL, Red-Black)
- Lock-free implementation possible
- Good cache locality
- Probabilistic balancing (no rotations needed)

## Important Notes

**No null keys:**
```java
map.put(null, "value");  // NullPointerException
```

**Null values allowed:**
```java
map.put(1, null);  // OK
```

**Keys must be comparable:**
```java
// BAD - no natural ordering
class MyClass { }
ConcurrentSkipListMap<MyClass, String> map = new ConcurrentSkipListMap<>();
map.put(new MyClass(), "value");  // ClassCastException!

// GOOD - provide comparator
ConcurrentSkipListMap<MyClass, String> map = 
    new ConcurrentSkipListMap<>(comparator);
```

**Weakly consistent iterators:**
```java
// Iterator may not reflect concurrent modifications
for (Map.Entry<Integer, String> entry : map.entrySet()) {
    // May not see all concurrent updates
}
```

**SubMap is a view:**
```java
NavigableMap<Integer, String> subMap = map.subMap(2, 5);
subMap.clear();  // Removes entries from original map too!
```

## Real-World Example: Stock Price Monitor

```java
class StockPrice {
    private String symbol;
    private double price;
    private long timestamp;
    
    public StockPrice(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    public long getTimestamp() { return timestamp; }
}

public class StockPriceMonitor {
    // Price -> StockPrice (sorted by price)
    private ConcurrentSkipListMap<Double, StockPrice> priceMap = 
        new ConcurrentSkipListMap<>();
    
    public void updatePrice(String symbol, double price) {
        // Remove old entry for this symbol
        priceMap.values().removeIf(sp -> sp.getSymbol().equals(symbol));
        // Add new entry
        priceMap.put(price, new StockPrice(symbol, price));
        System.out.println(symbol + " updated to $" + price);
    }
    
    public void getStocksInPriceRange(double minPrice, double maxPrice) {
        System.out.println("\nStocks between $" + minPrice + " and $" + maxPrice + ":");
        priceMap.subMap(minPrice, true, maxPrice, true)
            .forEach((price, stock) -> 
                System.out.println(stock.getSymbol() + ": $" + price)
            );
    }
    
    public StockPrice getCheapestStock() {
        Map.Entry<Double, StockPrice> first = priceMap.firstEntry();
        return first != null ? first.getValue() : null;
    }
    
    public StockPrice getMostExpensiveStock() {
        Map.Entry<Double, StockPrice> last = priceMap.lastEntry();
        return last != null ? last.getValue() : null;
    }
    
    public static void main(String[] args) {
        StockPriceMonitor monitor = new StockPriceMonitor();
        
        monitor.updatePrice("AAPL", 150.25);
        monitor.updatePrice("GOOGL", 2800.50);
        monitor.updatePrice("MSFT", 300.75);
        monitor.updatePrice("TSLA", 700.00);
        
        monitor.getStocksInPriceRange(200, 800);
        
        StockPrice cheapest = monitor.getCheapestStock();
        System.out.println("\nCheapest: " + cheapest.getSymbol() + 
                         " at $" + cheapest.getPrice());
    }
}
```

## Testing

```java
@Test
public void testSortedOrder() {
    ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
    
    map.put(3, "Three");
    map.put(1, "One");
    map.put(2, "Two");
    
    List<Integer> keys = new ArrayList<>(map.keySet());
    assertEquals(Arrays.asList(1, 2, 3), keys);
}

@Test
public void testConcurrentAccess() throws InterruptedException {
    ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
    int numThreads = 10;
    int itemsPerThread = 100;
    
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < numThreads; i++) {
        int threadId = i;
        Thread t = new Thread(() -> {
            for (int j = 0; j < itemsPerThread; j++) {
                map.put(threadId * 1000 + j, "Value");
            }
        });
        threads.add(t);
        t.start();
    }
    
    for (Thread t : threads) {
        t.join();
    }
    
    assertEquals(numThreads * itemsPerThread, map.size());
}

@Test
public void testNavigableOperations() {
    ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
    map.put(10, "Ten");
    map.put(20, "Twenty");
    map.put(30, "Thirty");
    
    assertEquals(Integer.valueOf(20), map.floorKey(25));
    assertEquals(Integer.valueOf(30), map.ceilingKey(25));
    assertEquals(Integer.valueOf(10), map.lowerKey(20));
    assertEquals(Integer.valueOf(30), map.higherKey(20));
}
```

## Quick Reference

```java
// Create
ConcurrentSkipListMap<K, V> map = new ConcurrentSkipListMap<>();
ConcurrentSkipListMap<K, V> map = new ConcurrentSkipListMap<>(comparator);

// Basic operations
map.put(key, value);
V value = map.get(key);
V removed = map.remove(key);
boolean has = map.containsKey(key);

// Navigable operations
K first = map.firstKey();
K last = map.lastKey();
K floor = map.floorKey(key);
K ceiling = map.ceilingKey(key);
K lower = map.lowerKey(key);
K higher = map.higherKey(key);

// Range views
NavigableMap<K, V> sub = map.subMap(from, to);
NavigableMap<K, V> head = map.headMap(to);
NavigableMap<K, V> tail = map.tailMap(from);

// Descending
NavigableMap<K, V> desc = map.descendingMap();
```

## Summary

- **Thread-safe sorted map** - concurrent access with ordering
- **Lock-free** - uses CAS operations for better performance
- **O(log n) operations** - efficient search, insert, delete
- **Navigable** - floor, ceiling, range queries
- **Skip list structure** - probabilistic balancing
- **Perfect for** - leaderboards, timelines, sorted concurrent data
- **No null keys** - throws NullPointerException
- **Better than TreeMap** - for concurrent access
- **Use ConcurrentHashMap** - if order doesn't matter (faster)