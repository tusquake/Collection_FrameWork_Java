import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class HasSetDemo {
    public static void main(String[] args) {

        ArrayList al = new ArrayList<>();
        al.add("Tushar");
        al.add("Seth");
        al.add(21);

        HashSet hs = new HashSet(al);

        hs.add(10);
        hs.add("Tushar");
        hs.add(100.55);
        hs.add(100);
        hs.add(true);

        System.out.println(hs);
        hs.add(100.55);
        System.out.println(hs);

        Iterator itr = hs.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        hs.remove("Seth");
        System.out.println(hs.contains("Seth"));
        System.out.println(hs.size());
    }
}
