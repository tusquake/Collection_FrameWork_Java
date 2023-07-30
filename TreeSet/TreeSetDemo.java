import java.util.TreeSet;

class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<Integer>();
        ts.add(10);
        ts.add(50);
        ts.add(30);
        ts.add(40);
        ts.add(60);
        ts.add(20);
        ts.add(10);

        System.out.println(ts);

        ts.clear();

        System.out.println(ts);
        System.out.println(ts.size());
    }

}