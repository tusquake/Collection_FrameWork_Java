import java.util.Hashtable;

class HashtableDemo {
    public static void main(String[] args) {
        Hashtable ht = new Hashtable();
        ht.put(21, "Tussu");
        ht.put(23, "Sussu");
        ht.put(25, "Bussu");
        ht.put(21, "Lussu");
        ht.put(21, "Vussu");

        System.out.println(ht);
    }
}