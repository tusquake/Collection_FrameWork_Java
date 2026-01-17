package PriorityBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class Task implements Comparable<Task> {

    int priority;
    String name;

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task other) {
        // Lower value = higher priority
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return name + " (priority=" + priority + ")";
    }
}

class Producer implements Runnable {

    private PriorityBlockingQueue<Task> queue;

    Producer(PriorityBlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put(new Task("Low priority task", 3));
            queue.put(new Task("High priority task", 1));
            queue.put(new Task("Medium priority task", 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {

    private PriorityBlockingQueue<Task> queue;

    Consumer(PriorityBlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = queue.take(); // blocks if empty
                System.out.println("Processing: " + task);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class PriorityBlockingQueueExample {

    public static void main(String[] args) throws InterruptedException {

        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));

        producer.start();
        consumer.start();
        Thread.sleep(5000);
        System.exit(0);
    }
}
