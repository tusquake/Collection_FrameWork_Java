import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

class HashmapDemo {
    public static void main(String[] args) {
        HashMap<Integer, String> hm = new HashMap();

        hm.put(101, "Tushu");
        hm.put(102, "Mushu");
        hm.put(103, "Rushu");
        hm.put(104, "Pushu");

        System.out.println(hm);

        for (Map.Entry me : hm.entrySet()) {
            System.out.println(me.getKey() + " => " + me.getValue());
        }

        // Set set = hm.entrySet();
        // System.out.println(set);

        // Iterator itr = set.iterator();
        // while (itr.hasNext()) {
        // Map.Entry entry = (Map.Entry) itr.next();
        // System.out.println(entry.getKey());
        // }
    }
}
