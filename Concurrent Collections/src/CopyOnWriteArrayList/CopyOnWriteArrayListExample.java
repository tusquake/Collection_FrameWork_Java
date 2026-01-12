package CopyOnWriteArrayList;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {

    public static void main(String[] args) throws InterruptedException {

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        list.add("Java");
        list.add("Spring");

        // Reader task
        Runnable reader = () -> {
            for (String item : list) {
                System.out.println(Thread.currentThread().getName() + " reading: " + item);
                sleep();
            }
        };

        // Writer task
        Runnable writer = () -> {
            sleep();
            list.add("Microservices");
            System.out.println(Thread.currentThread().getName() + " added: Microservices");
        };

        Thread t1 = new Thread(reader, "Reader-1");
        Thread t2 = new Thread(reader, "Reader-2");
        Thread t3 = new Thread(writer, "Writer");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final List: " + list);
    }

    private static void sleep() {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
