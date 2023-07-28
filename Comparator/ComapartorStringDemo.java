import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComapartorStringDemo {
    public static void main(String[] args) {

        Comparator<String> cmp = new Comparator<String>() {
            public int compare(String a, String b) {
                if (a.length() > b.length()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };

        List<String> list = new ArrayList<>();
        list.add("Ram");
        list.add("Shankar");
        list.add("Shyam");
        list.add("Ganesh");
        list.add("Hanuman");

        Collections.sort(list, cmp);
        System.out.println(list);

    }
}
