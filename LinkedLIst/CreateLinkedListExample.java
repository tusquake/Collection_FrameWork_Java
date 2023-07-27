//add()
//add(location,element)
//addFirst()
//addLast()

import java.util.LinkedList;

class CreateLinkedListExample {
    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();

        fruits.add("Banana");
        fruits.add("Apple");
        fruits.add("Mango");

        System.out.println(fruits);

        // Adding Element at Specific Element in the LinkedList
        fruits.add(2, "Watermelon");
        System.out.println(fruits);

        // Adding Element at Beginning of LinkedList
        fruits.addFirst("Stawbeerry");
        System.out.println(fruits);

        // Adding Element at End of LinkedList
        fruits.addLast("Melon");
        System.out.println(fruits);
    }
}