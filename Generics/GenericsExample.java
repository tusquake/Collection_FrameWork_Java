public class GenericsExample {
    public static void main(String[] args) {
        IntegerPrinter printer = new IntegerPrinter(23);
        printer.print();

        DoublePrinter dprinter = new DoublePrinter(23.0011);
        dprinter.print();

        Printer<Integer> printerT = new Printer<>(23);
        printerT.print();

        Printer<Double> printerTd = new Printer<>(23.01);
        printerTd.print();
    }
}
