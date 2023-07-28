import java.util.*;

class StackDemo {
    public static void main(String[] args) {
        Stack s = new Stack();
        s.push("Tushar");
        s.push("Seth");
        s.push(21);
        s.push(78.9);

        System.out.println(s.isEmpty());
        System.out.println(s);
        System.out.println(s.pop());
        System.out.println(s);
        System.out.println(s.peek());
        System.out.println(s);
        System.out.println(s.search(21));// 1 based indexing
    }
}