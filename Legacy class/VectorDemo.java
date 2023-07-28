import java.util.Vector;

public class VectorDemo {
    public static void main(String[] args) {
        Vector v = new Vector();

        v.add(10);
        v.add(1, "Tushar");
        v.addElement(20);
        v.addElement(210);
        v.add("Seth");
        // v.addElement(2,"Seth"); Not valid
        // v.addElementAt(v);
        System.out.println(v);
        v.removeElementAt(2);
        System.out.println(v);
        System.out.println(v.capacity());
        System.out.println(v.size());
        v.setElementAt("Tussu", 1);
        System.out.println(v);
    }
}
