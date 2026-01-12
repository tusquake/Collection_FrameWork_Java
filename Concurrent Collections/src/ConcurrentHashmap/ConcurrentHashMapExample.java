package ConcurrentHashmap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<String, Integer> loginCountMap = new ConcurrentHashMap<>();

        // Task: increment login count for a user
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                loginCountMap.merge("tushar", 1, Integer::sum);
            }
        };

        // Creating multiple threads
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        // Final result
        System.out.println("Final login count: " + loginCountMap.get("tushar"));
    }
}

