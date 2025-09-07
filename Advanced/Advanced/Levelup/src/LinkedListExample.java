import java.util.*;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        list.add("First");
        list.addFirst("New First");
        list.addLast("Last");
        list.add(1, "Middle");

        System.out.println("List: " + list);

        list.offer("Queue Element");
        String first = list.poll();
        System.out.println("Polled: " + first);

        list.push("Stack Top");
        String top = list.pop();
        System.out.println("Popped: " + top);

        System.out.println("First: " + list.peekFirst());
        System.out.println("Last: " + list.peekLast());

        Iterator<String> it = list.descendingIterator();
        System.out.println("Reverse order:");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}