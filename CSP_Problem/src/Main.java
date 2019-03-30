import helpers.IO;
import helpers.Printer;

public class Main {

    public static void main(String[] args)
    {
        IO.readFutoshikiData("futoshiki_5_3.txt");
        IO.readSkyscrapperData("skyscrapper_4_0.txt");
        Printer.printFutoshiki(IO.matrix, IO.constraints);
        System.out.println("KONIEC");
    }
}
