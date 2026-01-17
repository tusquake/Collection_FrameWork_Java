package ConcurrentSkipListSet;

import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetExample {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();

        // Producer thread
        Runnable writer = () -> {
            set.add(50);
            set.add(20);
            set.add(30);
            System.out.println(Thread.currentThread().getName() + " added elements");
        };

        // Reader thread
        Runnable reader = () -> {
            try {
                Thread.sleep(500);
                set.forEach(e -> System.out.println("Set Element: " + e));
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

        // Extra operations
        System.out.println("First element: " + set.first());
        System.out.println("Last element: " + set.last());
    }
}
