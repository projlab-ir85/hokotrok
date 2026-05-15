package Main;

import Control.Controller;

import javax.swing.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

        boolean graphical = List.of(args).contains("--graphical");

        //for testing only
        graphical = true;

        Controller controller = new Controller(graphical);
        try{
            controller.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
