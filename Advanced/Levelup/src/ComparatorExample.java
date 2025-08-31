import java.util.*;

//class Student {
//    String name;
//    int age;
//    double gpa;
//
//    public Student(String name, int age, double gpa) {
//        this.name = name;
//        this.age = age;
//        this.gpa = gpa;
//    }
//
//    @Override
//    public String toString() {
//        return name + "(" + age + ", " + gpa + ")";
//    }
//}

class MyIntegerComparator implements Comparator<Integer>{
    @Override
    public int compare(Integer o1, Integer o2){
        return o2-o1;
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Integer> o = new ArrayList<Integer>();
            o.add(1);
            o.add(4);
            o.add(8);
            o.add(5);
            o.sort(new MyIntegerComparator());
        System.out.println(o);
//        List<Student> students = new ArrayList<>();
//        students.add(new Student("Alice", 22, 3.8));
//        students.add(new Student("Bob", 20, 3.9));
//        students.add(new Student("Charlie", 21, 3.7));
//
//        students.sort(Comparator.comparingInt(s -> s.age));
//        System.out.println("By age: " + students);
//
//        students.sort(Comparator.comparingDouble((Student s) -> s.gpa).reversed());
//        System.out.println("By GPA desc: " + students);
//
//        students.sort(Comparator.comparingDouble((Student s) -> s.gpa)
//                .thenComparingInt(s -> s.age));
//
//        students.sort((s1, s2) -> s1.name.compareTo(s2.name));
    }
}