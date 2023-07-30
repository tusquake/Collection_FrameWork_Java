import java.util.HashMap;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        Map map = new HashMap();

        map.put(101, "Tussu");
        map.put(102, "Hussu");
        map.put(103, "Mussu");
        map.put(102, "Hussu");
        map.put(104, "Hussu");
        map.put(null, null);
        map.put(null, "aa");
        System.out.println(map);
        System.out.println(map.containsKey(100));
        System.out.println(map.containsKey(101));
        System.out.println(map.containsValue("Yussus"));
        System.out.println(map.containsValue("Tussu"));
        System.out.println(map.get(101));
        System.out.println(map);
        map.replace(101, "Khussu");
        System.out.println(map);
        map.clear();
        System.out.println(map.size());

    }
}