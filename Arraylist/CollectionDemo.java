import java.util.ArrayList;
import java.util.Collection;

public class CollectionDemo {
    public static void main(String[] args) {
        Collection<String> fruitsCollection = new ArrayList<>();
        fruitsCollection.add("banana");
        fruitsCollection.add("apple");
        fruitsCollection.add("mango");

        System.out.println(fruitsCollection);

        fruitsCollection.remove("apple");
        System.out.println(fruitsCollection);

        System.out.println(fruitsCollection.contains("banana"));

        System.out.println(fruitsCollection.isEmpty());

        fruitsCollection.clear();
        System.out.println(fruitsCollection);

    }
}
