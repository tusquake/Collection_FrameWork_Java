import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class ComparatorDemo {

    public static void main(String[] args) {
        Comparator<Integer> com = new Comparator<Integer>() {
            public int compare(Integer i, Integer j) {
                if (i % 10 > j % 10) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };

        List<Integer> list = new ArrayList<>();
        list.add(21);
        list.add(39);
        list.add(12);
        list.add(55);

        Collections.sort(list);
        System.out.println(list);
        Collections.sort(list, com);
        System.out.println(list);

    }
}