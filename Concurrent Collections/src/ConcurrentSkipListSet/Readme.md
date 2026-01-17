# ConcurrentSkipListSet

## What is it?

Thread-safe, **sorted** set based on **skip list** data structure. Provides **O(log n)** performance for most operations.

**Core Concept**: A probabilistic data structure with multiple levels of linked lists that allows fast search, insert, and delete while maintaining sorted order. Thread-safe alternative to TreeSet.

## Key Features

- **Thread-safe**: Multiple threads can read/write concurrently
- **Sorted**: Elements always sorted (natural order or comparator)
- **Lock-free**: Uses CAS (Compare-And-Swap) operations
- **O(log n) operations**: add, remove, contains
- **No null elements**: Cannot add null
- **Navigable**: Supports range queries, floor, ceiling operations
- **No duplicates**: Set semantics (unique elements only)
- **Scalable**: Better than TreeSet for concurrent access

## Basic Example

```java
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetExample {
    public static void main(String[] args) {
        ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
        
        // Add elements (automatically sorted)
        set.add(3);
        set.add(1);
        set.add(5);
        set.add(2);
        set.add(4);
        set.add(2);  // Duplicate - won't be added
        
        // Iterate - always in sorted order
        set.forEach(num -> System.out.println(num));
        
        // Output (sorted):
        // 1
        // 2
        // 3
        // 4
        // 5
    }
}
```

## Common Operations

```java
ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();

// Add
boolean added = set.add(1);           // true
boolean duplicate = set.add(1);       // false (already exists)

// Remove
boolean removed = set.remove(1);      // true
boolean notFound = set.remove(10);    // false

// Contains
boolean has = set.contains(1);

// Size
int size = set.size();
boolean empty = set.isEmpty();

// Clear
set.clear();

// Convert to array
Integer[] array = set.toArray(new Integer[0]);
```

## Navigable Operations

```java
ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
set.add(10);
set.add(20);
set.add(30);
set.add(40);

// First and Last
Integer first = set.first();      // 10
Integer last = set.last();        // 40

// Floor (<=) and Ceiling (>=)
Integer floor25 = set.floor(25);     // 20 (largest <= 25)
Integer ceiling25 = set.ceiling(25); // 30 (smallest >= 25)

// Lower (<) and Higher (>)
Integer lower30 = set.lower(30);     // 20 (largest < 30)
Integer higher30 = set.higher(30);   // 40 (smallest > 30)

// Poll (remove and return)
Integer polledFirst = set.pollFirst();  // Remove and return 10
Integer polledLast = set.pollLast();    // Remove and return 40
```

## Range Views (SubSets)

```java
ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
set.add(1);
set.add(2);
set.add(3);
set.add(4);
set.add(5);

// SubSet (inclusive from, exclusive to)
NavigableSet<Integer> subSet = set.subSet(2, 4);
// Contains: [2, 3]

// HeadSet (less than)
NavigableSet<Integer> headSet = set.headSet(3);
// Contains: [1, 2]

// TailSet (greater than or equal)
NavigableSet<Integer> tailSet = set.tailSet(3);
// Contains: [3, 4, 5]

// Descending view
NavigableSet<Integer> descending = set.descendingSet();
// Reverse order: [5, 4, 3, 2, 1]
```

## Real-World Example: Active User Sessions

```java
class UserSession implements Comparable<UserSession> {
    private String userId;
    private long loginTime;
    
    public UserSession(String userId) {
        this.userId = userId;
        this.loginTime = System.currentTimeMillis();
    }
    
    public String getUserId() { return userId; }
    public long getLoginTime() { return loginTime; }
    
    @Override
    public int compareTo(UserSession other) {
        // Sort by login time
        int timeCompare = Long.compare(this.loginTime, other.loginTime);
        // If same time, sort by userId to ensure uniqueness
        return timeCompare != 0 ? timeCompare : this.userId.compareTo(other.userId);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSession)) return false;
        UserSession that = (UserSession) o;
        return userId.equals(that.userId);
    }
    
    @Override
    public int hashCode() {
        return userId.hashCode();
    }
    
    @Override
    public String toString() {
        return userId + " (logged in at " + loginTime + ")";
    }
}

public class SessionManager {
    private ConcurrentSkipListSet<UserSession> activeSessions = 
        new ConcurrentSkipListSet<>();
    
    public void login(String userId) {
        UserSession session = new UserSession(userId);
        activeSessions.add(session);
        System.out.println("User logged in: " + userId);
    }
    
    public void logout(String userId) {
        activeSessions.removeIf(session -> session.getUserId().equals(userId));
        System.out.println("User logged out: " + userId);
    }
    
    public void printActiveSessions() {
        System.out.println("\nActive sessions (chronological):");
        activeSessions.forEach(System.out::println);
    }
    
    public void cleanupOldSessions(long maxAgeMillis) {
        long cutoffTime = System.currentTimeMillis() - maxAgeMillis;
        UserSession cutoff = new UserSession("") {
            @Override
            public long getLoginTime() {
                return cutoffTime;
            }
        };
        
        NavigableSet<UserSession> oldSessions = activeSessions.headSet(cutoff);
        int removed = oldSessions.size();
        oldSessions.clear();
        System.out.println("Removed " + removed + " expired sessions");
    }
    
    public int getActiveUserCount() {
        return activeSessions.size();
    }
    
    public static void main(String[] args) throws InterruptedException {
        SessionManager manager = new SessionManager();
        
        // Simulate concurrent logins
        Thread t1 = new Thread(() -> {
            manager.login("alice");
            manager.login("bob");
        });
        
        Thread t2 = new Thread(() -> {
            manager.login("charlie");
            manager.login("diana");
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        manager.printActiveSessions();
        System.out.println("\nTotal active users: " + manager.getActiveUserCount());
    }
}
```

## Real-World Example: Priority Task Queue

```java
class Task implements Comparable<Task> {
    private String id;
    private int priority;
    private long createdAt;
    
    public Task(String id, int priority) {
        this.id = id;
        this.priority = priority;
        this.createdAt = System.currentTimeMillis();
    }
    
    public String getId() { return id; }
    public int getPriority() { return priority; }
    
    @Override
    public int compareTo(Task other) {
        // Higher priority first (descending)
        int priorityCompare = Integer.compare(other.priority, this.priority);
        if (priorityCompare != 0) return priorityCompare;
        
        // Same priority: older tasks first (ascending)
        int timeCompare = Long.compare(this.createdAt, other.createdAt);
        if (timeCompare != 0) return timeCompare;
        
        // Same priority and time: sort by ID
        return this.id.compareTo(other.id);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        return id.equals(((Task) o).id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return "Task{id='" + id + "', priority=" + priority + "}";
    }
}

public class PriorityTaskQueue {
    private ConcurrentSkipListSet<Task> taskQueue = new ConcurrentSkipListSet<>();
    
    public void addTask(String id, int priority) {
        Task task = new Task(id, priority);
        if (taskQueue.add(task)) {
            System.out.println("Added: " + task);
        } else {
            System.out.println("Task already exists: " + id);
        }
    }
    
    public Task getNextTask() {
        Task task = taskQueue.pollFirst();
        if (task != null) {
            System.out.println("Processing: " + task);
        }
        return task;
    }
    
    public void printQueue() {
        System.out.println("\nCurrent queue (priority order):");
        taskQueue.forEach(System.out::println);
    }
    
    public void removeTask(String id) {
        taskQueue.removeIf(task -> task.getId().equals(id));
        System.out.println("Removed task: " + id);
    }
    
    public int getQueueSize() {
        return taskQueue.size();
    }
    
    public NavigableSet<Task> getHighPriorityTasks(int minPriority) {
        Task threshold = new Task("", minPriority) {
            @Override
            public int getPriority() {
                return minPriority;
            }
        };
        return taskQueue.headSet(threshold, true);
    }
    
    public static void main(String[] args) {
        PriorityTaskQueue queue = new PriorityTaskQueue();
        
        queue.addTask("task1", 5);
        queue.addTask("task2", 10);
        queue.addTask("task3", 3);
        queue.addTask("task4", 10);  // Same priority as task2
        queue.addTask("task5", 7);
        
        queue.printQueue();
        
        System.out.println("\nProcessing tasks:");
        queue.getNextTask();
        queue.getNextTask();
        
        queue.printQueue();
    }
}
```

## Real-World Example: Unique Score Tracker

```java
public class UniqueScoreTracker {
    private ConcurrentSkipListSet<Integer> scores = new ConcurrentSkipListSet<>();
    
    public void recordScore(int score) {
        if (scores.add(score)) {
            System.out.println("New score recorded: " + score);
        } else {
            System.out.println("Score already exists: " + score);
        }
    }
    
    public void printScores() {
        System.out.println("\nAll unique scores:");
        scores.forEach(System.out::println);
    }
    
    public int getHighestScore() {
        return scores.isEmpty() ? 0 : scores.last();
    }
    
    public int getLowestScore() {
        return scores.isEmpty() ? 0 : scores.first();
    }
    
    public NavigableSet<Integer> getScoresAbove(int threshold) {
        return scores.tailSet(threshold, false);
    }
    
    public NavigableSet<Integer> getScoresBelow(int threshold) {
        return scores.headSet(threshold, false);
    }
    
    public NavigableSet<Integer> getScoresBetween(int min, int max) {
        return scores.subSet(min, true, max, true);
    }
    
    public int countScoresAbove(int threshold) {
        return getScoresAbove(threshold).size();
    }
    
    public static void main(String[] args) {
        UniqueScoreTracker tracker = new UniqueScoreTracker();
        
        tracker.recordScore(85);
        tracker.recordScore(92);
        tracker.recordScore(78);
        tracker.recordScore(92);  // Duplicate
        tracker.recordScore(88);
        tracker.recordScore(95);
        
        tracker.printScores();
        
        System.out.println("\nHighest: " + tracker.getHighestScore());
        System.out.println("Lowest: " + tracker.getLowestScore());
        
        System.out.println("\nScores above 90:");
        tracker.getScoresAbove(90).forEach(System.out::println);
        
        System.out.println("\nScores between 80-90:");
        tracker.getScoresBetween(80, 90).forEach(System.out::println);
    }
}
```

## ConcurrentSkipListSet vs Others

| Feature | ConcurrentSkipListSet | ConcurrentHashMap.newKeySet() | TreeSet |
|---------|----------------------|-------------------------------|---------|
| Thread-safe | Yes | Yes | No |
| Sorted | Yes | No | Yes |
| Locking | Lock-free (CAS) | Lock-free (CAS) | External sync needed |
| Performance | O(log n) | O(1) average | O(log n) |
| Null elements | No | No | No (with comparator) |
| Navigable | Yes | No | Yes |
| Best for | Sorted concurrent | Unsorted concurrent | Sorted single-thread |

## When to Use

**Use ConcurrentSkipListSet when:**
- Need sorted set with concurrent access
- Unique elements in sorted order
- Range queries on sorted data
- Floor, ceiling, higher, lower operations
- Priority queues with unique elements
- Leaderboards with unique scores
- Time-ordered unique events
- Active user tracking (sorted by login time)

**Avoid when:**
- Don't need sorted order (use ConcurrentHashMap.newKeySet() - faster)
- Single-threaded (use TreeSet - simpler)
- Need null elements
- Performance-critical and order doesn't matter
- Need duplicates (use ConcurrentSkipListMap instead)

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

**No null elements:**
```java
set.add(null);  // NullPointerException
```

**Elements must be comparable:**
```java
// BAD - no natural ordering
class MyClass { }
ConcurrentSkipListSet<MyClass> set = new ConcurrentSkipListSet<>();
set.add(new MyClass());  // ClassCastException!

// GOOD - provide comparator
ConcurrentSkipListSet<MyClass> set = 
    new ConcurrentSkipListSet<>(comparator);
```

**Equals and compareTo must be consistent:**
```java
// For proper set behavior:
// a.equals(b) should be true if and only if a.compareTo(b) == 0
```

**Weakly consistent iterators:**
```java
// Iterator may not reflect concurrent modifications
for (Integer num : set) {
    // May not see all concurrent updates
}
```

**SubSet is a view:**
```java
NavigableSet<Integer> subSet = set.subSet(2, 5);
subSet.clear();  // Removes elements from original set too!
```

## Real-World Example: IP Address Blocklist

```java
public class IPBlocklist {
    private ConcurrentSkipListSet<String> blockedIPs = new ConcurrentSkipListSet<>();
    
    public void blockIP(String ip) {
        if (blockedIPs.add(ip)) {
            System.out.println("Blocked: " + ip);
        } else {
            System.out.println("Already blocked: " + ip);
        }
    }
    
    public void unblockIP(String ip) {
        if (blockedIPs.remove(ip)) {
            System.out.println("Unblocked: " + ip);
        } else {
            System.out.println("Not in blocklist: " + ip);
        }
    }
    
    public boolean isBlocked(String ip) {
        return blockedIPs.contains(ip);
    }
    
    public void printBlocklist() {
        System.out.println("\nBlocked IPs:");
        blockedIPs.forEach(System.out::println);
    }
    
    public NavigableSet<String> getBlockedIPsInRange(String startIP, String endIP) {
        return blockedIPs.subSet(startIP, true, endIP, true);
    }
    
    public int getBlockedCount() {
        return blockedIPs.size();
    }
    
    public static void main(String[] args) {
        IPBlocklist blocklist = new IPBlocklist();
        
        blocklist.blockIP("192.168.1.100");
        blocklist.blockIP("192.168.1.150");
        blocklist.blockIP("192.168.1.200");
        blocklist.blockIP("10.0.0.50");
        
        blocklist.printBlocklist();
        
        System.out.println("\nIs 192.168.1.150 blocked? " + 
            blocklist.isBlocked("192.168.1.150"));
        
        blocklist.unblockIP("192.168.1.150");
        
        System.out.println("\nBlocked IPs in 192.168.1.x range:");
        blocklist.getBlockedIPsInRange("192.168.1.0", "192.168.1.255")
            .forEach(System.out::println);
    }
}
```

## Comparison with ConcurrentSkipListMap

```java
// ConcurrentSkipListSet is backed by ConcurrentSkipListMap
ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();

// Internally uses:
// ConcurrentSkipListMap<String, Boolean> map = new ConcurrentSkipListMap<>();
// set.add("element") -> map.put("element", Boolean.TRUE)

// For key-value pairs, use ConcurrentSkipListMap
ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();

// For unique elements only, use ConcurrentSkipListSet
ConcurrentSkipListSet<String> uniqueElements = new ConcurrentSkipListSet<>();
```

## Testing

```java
@Test
public void testSortedOrder() {
    ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
    
    set.add(3);
    set.add(1);
    set.add(2);
    
    List<Integer> elements = new ArrayList<>(set);
    assertEquals(Arrays.asList(1, 2, 3), elements);
}

@Test
public void testNoDuplicates() {
    ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
    
    assertTrue(set.add(1));
    assertFalse(set.add(1));  // Duplicate
    assertEquals(1, set.size());
}

@Test
public void testConcurrentAccess() throws InterruptedException {
    ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
    int numThreads = 10;
    int itemsPerThread = 100;
    
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < numThreads; i++) {
        int threadId = i;
        Thread t = new Thread(() -> {
            for (int j = 0; j < itemsPerThread; j++) {
                set.add(threadId * 1000 + j);
            }
        });
        threads.add(t);
        t.start();
    }
    
    for (Thread t : threads) {
        t.join();
    }
    
    assertEquals(numThreads * itemsPerThread, set.size());
}

@Test
public void testNavigableOperations() {
    ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
    set.add(10);
    set.add(20);
    set.add(30);
    
    assertEquals(Integer.valueOf(20), set.floor(25));
    assertEquals(Integer.valueOf(30), set.ceiling(25));
    assertEquals(Integer.valueOf(10), set.lower(20));
    assertEquals(Integer.valueOf(30), set.higher(20));
}

@Test
public void testSubSetView() {
    ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);
    set.add(5);
    
    NavigableSet<Integer> subSet = set.subSet(2, 4);
    assertEquals(2, subSet.size());
    assertTrue(subSet.contains(2));
    assertTrue(subSet.contains(3));
    assertFalse(subSet.contains(4));
}
```

## Quick Reference

```java
// Create
ConcurrentSkipListSet<E> set = new ConcurrentSkipListSet<>();
ConcurrentSkipListSet<E> set = new ConcurrentSkipListSet<>(comparator);
ConcurrentSkipListSet<E> set = new ConcurrentSkipListSet<>(collection);

// Basic operations
boolean added = set.add(element);
boolean removed = set.remove(element);
boolean has = set.contains(element);
int size = set.size();
boolean empty = set.isEmpty();

// Navigable operations
E first = set.first();
E last = set.last();
E floor = set.floor(element);
E ceiling = set.ceiling(element);
E lower = set.lower(element);
E higher = set.higher(element);
E polledFirst = set.pollFirst();
E polledLast = set.pollLast();

// Range views
NavigableSet<E> sub = set.subSet(from, to);
NavigableSet<E> head = set.headSet(to);
NavigableSet<E> tail = set.tailSet(from);

// Descending
NavigableSet<E> desc = set.descendingSet();
Iterator<E> descIter = set.descendingIterator();
```

## Common Patterns

**Custom Comparator:**
```java
// Reverse order
ConcurrentSkipListSet<Integer> set = 
    new ConcurrentSkipListSet<>((a, b) -> Integer.compare(b, a));

// Case-insensitive strings
ConcurrentSkipListSet<String> set = 
    new ConcurrentSkipListSet<>(String.CASE_INSENSITIVE_ORDER);
```

**Batch Operations:**
```java
ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();

// Add all
set.addAll(Arrays.asList(1, 2, 3, 4, 5));

// Remove all
set.removeAll(Arrays.asList(2, 4));

// Retain only
set.retainAll(Arrays.asList(1, 3, 5, 7));
```

**Iteration:**
```java
// Forward iteration
for (Integer num : set) {
    System.out.println(num);
}

// Backward iteration
for (Integer num : set.descendingSet()) {
    System.out.println(num);
}

// Iterator
Iterator<Integer> iter = set.iterator();
while (iter.hasNext()) {
    System.out.println(iter.next());
}
```

## Summary

- **Thread-safe sorted set** - concurrent access with ordering
- **No duplicates** - set semantics enforced
- **Lock-free** - uses CAS operations for better performance
- **O(log n) operations** - efficient add, remove, contains
- **Navigable** - floor, ceiling, range queries
- **Skip list structure** - probabilistic balancing
- **Perfect for** - unique sorted concurrent data, priority queues, rankings
- **No null elements** - throws NullPointerException
- **Better than TreeSet** - for concurrent access
- **Use ConcurrentHashMap.newKeySet()** - if order doesn't matter (faster)
- **Backed by ConcurrentSkipListMap** - internally uses map implementation