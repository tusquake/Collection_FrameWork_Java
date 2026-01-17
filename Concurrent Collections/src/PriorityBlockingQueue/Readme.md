# PriorityBlockingQueue

## What is it?

Thread-safe, **unbounded** blocking queue that orders elements by **priority** (not FIFO). Uses a **heap** data structure.

**Core Concept**: Elements are ordered by natural ordering or custom comparator. Highest priority element is always retrieved first, regardless of insertion order.

## Key Features

- **Priority ordering**: Elements ordered by priority, not insertion order
- **Unbounded**: No capacity limit (grows as needed)
- **Blocking on take**: Blocks when empty, never blocks on put
- **Thread-safe**: Multiple producers/consumers
- **Heap-based**: Binary heap implementation for efficient priority retrieval
- **No null elements**: Cannot add null

## Basic Example

```java
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample {
    public static void main(String[] args) throws InterruptedException {
        // Natural ordering (integers: lowest first)
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        
        // Add elements in random order
        queue.put(5);
        queue.put(1);
        queue.put(3);
        queue.put(2);
        queue.put(4);
        
        // Take elements - comes out in priority order
        while (!queue.isEmpty()) {
            System.out.println(queue.take());  // Output: 1, 2, 3, 4, 5
        }
    }
}
```

## Priority with Custom Comparator

```java
// Task with priority
class Task implements Comparable<Task> {
    private String name;
    private int priority;  // Lower number = higher priority
    
    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
    
    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }
    
    public String getName() { return name; }
    public int getPriority() { return priority; }
}

public class TaskScheduler {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        
        // Add tasks in random order
        queue.put(new Task("Low priority task", 5));
        queue.put(new Task("Critical bug fix", 1));
        queue.put(new Task("Medium task", 3));
        queue.put(new Task("Urgent feature", 2));
        
        // Process tasks by priority
        while (!queue.isEmpty()) {
            Task task = queue.take();
            System.out.println("Processing: " + task.getName() + 
                             " (Priority: " + task.getPriority() + ")");
        }
        
        // Output:
        // Processing: Critical bug fix (Priority: 1)
        // Processing: Urgent feature (Priority: 2)
        // Processing: Medium task (Priority: 3)
        // Processing: Low priority task (Priority: 5)
    }
}
```

## Common Operations

```java
PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

// Add (never blocks, unbounded)
queue.put(10);
queue.offer(20);
queue.add(30);

// Take (blocks if empty)
Integer item = queue.take();

// Poll (non-blocking, returns null if empty)
Integer item = queue.poll();

// Timed poll
Integer item = queue.poll(5, TimeUnit.SECONDS);

// Peek (view highest priority without removing)
Integer top = queue.peek();

// Size
int size = queue.size();
```

## Real-World Example: Hospital Emergency Room

```java
class Patient implements Comparable<Patient> {
    private String name;
    private int severity;  // 1=Critical, 5=Minor
    
    public Patient(String name, int severity) {
        this.name = name;
        this.severity = severity;
    }
    
    @Override
    public int compareTo(Patient other) {
        // Lower severity number = higher priority
        return Integer.compare(this.severity, other.severity);
    }
    
    public String getName() { return name; }
    public int getSeverity() { return severity; }
}

public class EmergencyRoom {
    private PriorityBlockingQueue<Patient> patientQueue = new PriorityBlockingQueue<>();
    
    // Patients arrive (Producer)
    public void admitPatient(Patient patient) {
        patientQueue.put(patient);
        System.out.println("Admitted: " + patient.getName() + 
                         " (Severity: " + patient.getSeverity() + ")");
    }
    
    // Doctors treat patients (Consumer)
    public void treatPatients() {
        while (true) {
            try {
                Patient patient = patientQueue.take();  // Highest priority first
                System.out.println("Treating: " + patient.getName() + 
                                 " (Severity: " + patient.getSeverity() + ")");
                Thread.sleep(2000);  // Treatment time
                System.out.println("Discharged: " + patient.getName());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        EmergencyRoom er = new EmergencyRoom();
        
        // Start doctors
        for (int i = 0; i < 2; i++) {
            new Thread(() -> er.treatPatients(), "Doctor-" + i).start();
        }
        
        // Patients arrive
        er.admitPatient(new Patient("John", 3));    // Medium
        er.admitPatient(new Patient("Alice", 1));   // Critical
        er.admitPatient(new Patient("Bob", 5));     // Minor
        er.admitPatient(new Patient("Charlie", 2)); // Urgent
        
        // Alice (1) treated first, then Charlie (2), then John (3), then Bob (5)
    }
}
```

## Priority Ordering

```java
// Natural ordering (Comparable)
PriorityBlockingQueue<Integer> naturalOrder = new PriorityBlockingQueue<>();
// Integers: 1, 2, 3, 4, 5 (ascending)

// Custom comparator (reverse order)
PriorityBlockingQueue<Integer> reverseOrder = new PriorityBlockingQueue<>(
    10,  // initial capacity
    (a, b) -> Integer.compare(b, a)  // Reverse: highest first
);

// String comparator (by length)
PriorityBlockingQueue<String> byLength = new PriorityBlockingQueue<>(
    10,
    Comparator.comparingInt(String::length)
);
```

## PriorityBlockingQueue vs Others

| Feature | PriorityBlockingQueue | LinkedBlockingQueue | ArrayBlockingQueue |
|---------|----------------------|--------------------|--------------------|
| Ordering | Priority (heap) | FIFO | FIFO |
| Bounded | No (unbounded) | Optional | Yes (always) |
| Blocks on put | No | Yes (if bounded) | Yes |
| Blocks on take | Yes | Yes | Yes |
| Storage | Array-based heap | Linked nodes | Fixed array |
| Best for | Priority tasks | FIFO blocking | Fixed capacity |

## When to Use

**Use PriorityBlockingQueue when:**
- Need priority-based processing (not FIFO)
- Tasks have different urgency levels
- Want automatic sorting by priority
- Unbounded growth is acceptable
- Examples: task scheduling, event handling, triage systems

**Avoid when:**
- Need FIFO ordering (use LinkedBlockingQueue)
- Need bounded capacity (no native support)
- All elements have same priority (overhead not needed)
- Performance-critical and priority changes frequently

## How It Works Internally

```
Binary Heap Structure (min-heap for natural ordering):

       1
      / \
     2   3
    / \
   4   5

Array representation: [1, 2, 3, 4, 5]

Parent: index i
Left child: 2*i + 1
Right child: 2*i + 2

Operations:
- offer(): Add to end, bubble up - O(log n)
- take(): Remove root, move last to root, bubble down - O(log n)
- peek(): Return root - O(1)
```

**Why Heap?**
- Efficient priority retrieval: O(1) for peek, O(log n) for take
- Maintains partial ordering (only guarantees root is highest priority)
- Better than sorting entire list on every operation

## Important Notes

**Unbounded - can cause OutOfMemoryError:**
```java
// No capacity limit - dangerous in production
PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
// Can grow until memory exhausted

// Best practice: Monitor size and reject if too large
if (queue.size() < MAX_SIZE) {
    queue.put(task);
} else {
    // Reject or handle overflow
}
```

**Only blocks on take, not put:**
```java
queue.put(item);  // NEVER blocks (unbounded)
queue.take();     // BLOCKS if empty
```

**Elements must be comparable:**
```java
// BAD - no comparator or Comparable
PriorityBlockingQueue<MyClass> queue = new PriorityBlockingQueue<>();
queue.put(new MyClass());  // ClassCastException!

// GOOD - implement Comparable
class MyClass implements Comparable<MyClass> { ... }

// OR provide comparator
new PriorityBlockingQueue<>(10, comparator);
```

**Iterator doesn't guarantee order:**
```java
for (Task task : queue) {
    // NOT guaranteed to be in priority order!
    // Only take() guarantees priority order
}
```

**No null elements:**
```java
queue.put(null);  // NullPointerException
```

## Real-World Example: Print Job Scheduler

```java
class PrintJob implements Comparable<PrintJob> {
    private String document;
    private int priority;  // 1=High, 5=Low
    private int pages;
    
    public PrintJob(String document, int priority, int pages) {
        this.document = document;
        this.priority = priority;
        this.pages = pages;
    }
    
    @Override
    public int compareTo(PrintJob other) {
        // First by priority, then by pages (shorter first)
        if (this.priority != other.priority) {
            return Integer.compare(this.priority, other.priority);
        }
        return Integer.compare(this.pages, other.pages);
    }
    
    public String getDocument() { return document; }
    public int getPriority() { return priority; }
    public int getPages() { return pages; }
}

public class PrinterQueue {
    private PriorityBlockingQueue<PrintJob> printQueue = new PriorityBlockingQueue<>();
    
    public void submitJob(PrintJob job) {
        printQueue.put(job);
        System.out.println("Queued: " + job.getDocument() + 
                         " (Priority: " + job.getPriority() + 
                         ", Pages: " + job.getPages() + ")");
    }
    
    public void processPrintJobs() {
        while (true) {
            try {
                PrintJob job = printQueue.take();
                System.out.println("Printing: " + job.getDocument());
                Thread.sleep(job.getPages() * 100);  // Simulate printing
                System.out.println("Completed: " + job.getDocument());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        PrinterQueue printer = new PrinterQueue();
        
        new Thread(() -> printer.processPrintJobs()).start();
        
        printer.submitJob(new PrintJob("Report.pdf", 3, 50));
        printer.submitJob(new PrintJob("Invoice.pdf", 1, 5));     // High priority, few pages
        printer.submitJob(new PrintJob("Manual.pdf", 5, 200));
        printer.submitJob(new PrintJob("Contract.pdf", 1, 20));   // High priority
        
        // Order: Invoice (1,5), Contract (1,20), Report (3,50), Manual (5,200)
    }
}
```

## Testing

```java
@Test
public void testPriorityOrdering() throws InterruptedException {
    PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
    
    // Add in random order
    queue.put(5);
    queue.put(1);
    queue.put(3);
    
    // Should come out in priority order
    assertEquals(1, queue.take().intValue());
    assertEquals(3, queue.take().intValue());
    assertEquals(5, queue.take().intValue());
}

@Test
public void testCustomComparator() throws InterruptedException {
    // Reverse order (highest first)
    PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(
        10, (a, b) -> Integer.compare(b, a)
    );
    
    queue.put(1);
    queue.put(5);
    queue.put(3);
    
    assertEquals(5, queue.take().intValue());
    assertEquals(3, queue.take().intValue());
    assertEquals(1, queue.take().intValue());
}
```

## Quick Reference

```java
// Create
PriorityBlockingQueue<T> queue = new PriorityBlockingQueue<>();
PriorityBlockingQueue<T> queue = new PriorityBlockingQueue<>(initialCapacity);
PriorityBlockingQueue<T> queue = new PriorityBlockingQueue<>(capacity, comparator);

// Add (never blocks)
queue.put(item);
queue.offer(item);

// Take (blocks if empty)
T item = queue.take();

// Poll (non-blocking)
T item = queue.poll();
T item = queue.poll(5, TimeUnit.SECONDS);

// Peek
T top = queue.peek();  // Highest priority

// Size
int size = queue.size();
```

## Summary

- **Priority ordering** - elements ordered by priority, not FIFO
- **Unbounded** - no capacity limit, can grow indefinitely
- **Heap-based** - efficient O(log n) for add/remove
- **Only blocks on take** - put never blocks (unbounded)
- **Perfect for** - task scheduling, event processing, triage systems
- **Requires Comparable** - elements must be comparable or provide comparator
- **Monitor size in production** - unbounded can cause memory issues
- **Iterator order not guaranteed** - only take() gives priority order