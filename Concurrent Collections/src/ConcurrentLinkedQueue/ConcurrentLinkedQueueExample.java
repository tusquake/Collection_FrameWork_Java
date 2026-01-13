package ConcurrentLinkedQueue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExample {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        // Producer task
        Runnable producer = () -> {
            for (int i = 1; i <= 5; i++) {
                queue.offer("Task-" + i);
                System.out.println(Thread.currentThread().getName() + " produced: Task-" + i);
                sleep();
            }
        };

        // Consumer task
        Runnable consumer = () -> {
            for (int i = 1; i <= 5; i++) {
                String task;
                while ((task = queue.poll()) == null) {
                    // Wait until task is available
                }
                System.out.println(Thread.currentThread().getName() + " consumed: " + task);
            }
        };

        Thread producerThread = new Thread(producer, "Producer");
        Thread consumerThread = new Thread(consumer, "Consumer");

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        System.out.println("Queue after processing: " + queue);
    }

    private static void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
