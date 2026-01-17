package DelayQueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

// Task with delay
class DelayedTask implements Delayed {
    private final String name;
    private final long startTime;

    public DelayedTask(String name, long delayInSeconds) {
        this.name = name;
        this.startTime = System.currentTimeMillis() + delayInSeconds * 1000;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return name;
    }
}

public class DelayQueueExample {
    public static void main(String[] args) throws InterruptedException {

        DelayQueue<DelayedTask> queue = new DelayQueue<>();

        // Producer
        queue.put(new DelayedTask("Task 1", 3)); // 3 sec delay
        queue.put(new DelayedTask("Task 2", 1)); // 1 sec delay
        queue.put(new DelayedTask("Task 3", 5)); // 5 sec delay

        System.out.println("Tasks added to DelayQueue");

        // Consumer
        while (!queue.isEmpty()) {
            DelayedTask task = queue.take(); // blocks until delay expires
            System.out.println("Processing: " + task + " at " + System.currentTimeMillis()/1000);
        }
    }
}
