package LinkedBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueExample {

    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        // Producer
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Producing: " + i);

                    // Blocks if queue is FULL
                    queue.put(i);

                    System.out.println("Produced: " + i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Consumer (slow)
        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {

                    Thread.sleep(1000); // slow consumer

                    // Blocks if queue is EMPTY
                    int value = queue.take();

                    System.out.println("Consumed: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(producer, "Producer").start();
        new Thread(consumer, "Consumer").start();
    }
}
