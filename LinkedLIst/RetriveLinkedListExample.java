//How to get the first element in the LinkedList.
//How to get the last element in the LinkedList.
//How to get the element at a given index in the LinkedList.
// How to get all the elements from LinkedList

import java.util.LinkedList;

public class RetriveLinkedListExample {
    public static void main(String[] args) {
        LinkedList<String> fruits = new LinkedList<>();
        fruits.add("Banana");
        fruits.add("Apple");
        fruits.add("mango");
        fruits.add("Pineapple");

        // Retrive first element from linkedlist
        String firstElement = fruits.getFirst();
        System.out.println(firstElement);

        // Retrive last element from linkedlist
        String LastElement = fruits.getLast();
        System.out.println(LastElement);

        // Retrieve Element through index
        String elem = fruits.get(1);
        System.out.println(elem);

        // for each method for iterating over list
        for (String list : fruits) {
            System.out.println(list);
        }
    }
}
