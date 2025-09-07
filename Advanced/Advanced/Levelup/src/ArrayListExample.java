import java.util.*;

public class ArrayListExample {
    public static void main(String[] args) {
        ArrayList<String> fruits = new ArrayList<>();

        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");

        fruits.add(1, "Mango");

        System.out.println("First fruit: " + fruits.get(0));
        System.out.println("Size: " + fruits.size());

        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        fruits.remove("Banana");
        fruits.remove(0);

        if (fruits.contains("Cherry")) {
            System.out.println("Cherry found!");
        }
    }
}