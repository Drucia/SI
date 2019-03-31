import algorithm.CSP;
import helpers.IO;
import helpers.Printer;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args)
    {
        String name = "futoshiki_4_0.txt";
        // read from file
        IO.readFutoshikiData(name);
        //IO.readSkyscrapperData("skyscrapper_4_0.txt");

        System.out.println("\n------------------START PACK------------------\n");
        Printer.printFutoshiki(IO.matrix, IO.constraints);
        CSP.set_constans(IO.constraints, IO.dimension);
        System.out.println("\n-----------------BACKTRACKING-----------------\n");
        Printer.printFutoshiki(CSP.runBackTracking(IO.matrix), IO.constraints);
        System.out.println("Counter -> " + CSP.getCounter());
        System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());
        IO.readFutoshikiData(name);
        System.out.println("\n-----------------FORWARDING-----------------\n");
        Printer.printFutoshiki(CSP.runForwarding(IO.matrix), IO.constraints);
        System.out.println("Counter -> " + CSP.getCounter());
        System.out.println("Total Time [nano time] -> " + CSP.getTotalTime());
        //Printer.printSkyscrapper(IO.matrix, IO.constraints);
        System.out.println("\n-------------------KONIEC-------------------\n");
    }
}
