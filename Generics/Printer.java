public class Printer<T> {
    T thingtoprint;

    public Printer(T thingtoPrint) {
        this.thingtoprint = thingtoPrint;
    }

    public void print() {
        System.out.println(thingtoprint);
    }
}
