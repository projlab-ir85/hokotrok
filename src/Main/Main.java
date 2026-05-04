package Main;

import Control.Controller;
import Skeleton.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    /** A main függvény, itt indul a program
     * Példányosítja a teszteket és a skeletont
     * Meghívja a skeleton menu függvényét
     */
    public static void main(String[] args) throws Exception{
        /* A kimenetet UTF-8 kódolásra állítjuk, hogy a magyar
         * karakterek és a tesztek determinisztikusak legyenek
         * pipe-on keresztüli futtatás esetén is. */
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        /*Tests tests = new Tests();
        Skeleton sk = new Skeleton(tests);

        sk.menu();*/

        Controller controller = new Controller();
        controller.start();
    }
}
