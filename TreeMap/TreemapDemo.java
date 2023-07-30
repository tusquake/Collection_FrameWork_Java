import java.util.Comparator;
import java.util.TreeMap;

class TreemapDemo {
    public static void main(String[] args) {

        Comparator cm = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return b > a ? 1 : -1;
            }
        };

        TreeMap tm = new TreeMap();

        tm.put(106, "deepak");
        tm.put(103, "amit");
        tm.put(104, "mussu");
        tm.put(108, "tussu");

        System.out.println(tm);
        System.out.println(tm.ceilingEntry(102));

        TreeMap tmcmp = new TreeMap(cm);

        tmcmp.put(106, "deepak");
        tmcmp.put(103, "amit");
        tmcmp.put(104, "mussu");
        tmcmp.put(108, "tussu");

        System.out.println(tmcmp);
        System.out.println(tmcmp.ceilingEntry(105));

        System.out.println(tm.containsKey(104));

        System.out.println(tm.floorEntry(105));

        System.out.println(tm.get(104));

        System.out.println(tm.get(100));

        System.out.println(tm.headMap(105));

        System.out.println(tm.size());

        tm.remove(104);

        System.out.println(tm);

        tm.replace(103, "sussu");

        System.out.println(tm);

        System.out.println(tm.subMap(102, 107));

        tm.clear();
        System.out.println(tm);
    }
}