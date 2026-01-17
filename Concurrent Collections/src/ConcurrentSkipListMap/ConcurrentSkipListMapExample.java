package ConcurrentSkipListMap;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapExample {
    public static void main(String[] args) throws InterruptedException {

        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Producer thread
        Runnable writer = () -> {
            map.put(5, "Five");
            map.put(1, "One");
            map.put(3, "Three");
            System.out.println(Thread.currentThread().getName() + " added elements");
        };

        // Reader thread
        Runnable reader = () -> {
            try {
                Thread.sleep(500);
                map.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread t1 = new Thread(writer, "Writer");
        Thread t2 = new Thread(reader, "Reader");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Additional operations
        System.out.println("First key: " + map.firstKey());
        System.out.println("Last key: " + map.lastKey());
    }
}

