package Skeleton;

import Control.Colors;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Skeleton {
    /** attríbutumok:
     * Runnable lista ami a teszteket tartalmazza
     * Scanner a bemenet kezelésére
     * indent a behúzást kezeli
     */
    private List<Runnable> tests = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int indent = 0;

    /** Konstruktor
     * minden tesztesetet hozzáadunk a tests listánkhoz
     * @param t egy teszt példány amit
     */
    public Skeleton(Tests t){
        tests.add(t::test1);
        tests.add(t::test2);
        tests.add(t::test3);
        tests.add(t::test4);
        tests.add(t::test5);
        tests.add(t::test6);
        tests.add(t::test7);
        tests.add(t::test8);
        tests.add(t::test9);
        tests.add(t::test10);
        tests.add(t::test11);
        tests.add(t::test12);
        tests.add(t::test13);
        tests.add(t::test14);
        tests.add(t::test15);
        tests.add(t::test16);
        tests.add(t::test17);
        tests.add(t::test18);
        tests.add(t::test19);
        tests.add(t::test20);
        tests.add(t::test21);
        tests.add(t::test22);
        tests.add(t::test23);
    }
    /** Behúzás növelése, növeli a behúzást egyel
     */
    private static void increaseIndent(){
        indent += 1;
    }
    /** Behúzása csökkentése, csökkenti a behúzást
     * Ha 0-nál kisebb lenne a behúzás akkor visszaállítja 0-ra
     */
    private static void decreaseIndent(){
        indent -= 1;
        if(indent < 0) indent = 0;
    }
    /** Előre irányuló metódus hívás függvény
     * Kiírja a standard kimenetre, hogy ki hívta a metódust, kin hívta a metódust,
     * melyik metódust hívta. Ha kell akkor a behúzást is növeljük
     * @param caller a metódus hívója
     * @param callee akin hívták a metódust
     * @param method melyik metódust hívták
     * @param increase igaz ha növelni kell a behúzást
     */
    public static void call(String caller, String callee, String method, boolean increase){
        System.out.println("\t".repeat(indent)
                + Colors.ORANGE + caller + Colors.RESETCOLOR
                + " --> "
                + Colors.ORANGE + callee + Colors.RESETCOLOR
                + "."
                + Colors.BLUE + method + Colors.RESETCOLOR
        );

        if(increase) increaseIndent();
    }
    /** Visszafelé irányuló metódus hívás függvény
     * Kiírja a standard kimenetre, hogy ki hívta a metódust, kin hívta a metódust,
     * melyik metódust hívta. Azt is megadja mi a hívás visszatérési értéke
     * @param caller a metódus hívója
     * @param callee akin hívták a metódust
     * @param method melyik metódust hívták
     * @param result
     */
    public static void returnCall(String caller, String callee, String method, String result){
        decreaseIndent();
        System.out.println("\t".repeat(indent)
                + Colors.ORANGE + callee + Colors.RESETCOLOR
                + " <-- "
                + Colors.ORANGE + caller + Colors.RESETCOLOR
                + "."
                + Colors.BLUE + method + Colors.RESETCOLOR
                + ": "
                + Colors.PURPLE + result + Colors.RESETCOLOR
        );
    }
    /** A program főmenüje
     * Felsorolja a teszteket, majd várja, hogy a tesztelő válasszon
     * A tesztelő beírja teszt számát és ekkor a program lefuttatja az adott tesztet
     * Ha a tesztelő rossz számot adott akkor jelzi a hibát
     */
    public void menu(){
        System.out.println(Colors.GREEN + "[MENU]");

        for(int i = 0; i < tests.size(); i++){
            System.out.println(Colors.BLUE + (i+1) + Colors.RESETCOLOR + " - test" + (i+1));
        }

        System.out.print(">");

        int index = scanner.nextInt();

        if(index < 1 || index > tests.size()){
            System.out.println(Colors.RED + "Invalid index." + Colors.RESETCOLOR);
            return;
        }

        info("Started test"+(index));
        tests.get(index-1).run();
        decreaseIndent();
    }
    /** Információt ír ki a standart kimenetre
     * @param message az üzenet amit kiír
     */
    private static void info(String message){
        System.out.println(Colors.YELLOW + "[INFO] " + Colors.RESETCOLOR + message + "\n" + Colors.RESETCOLOR);
    }
    /** Kérdés függvény
     * Kiírja a szabványos kimenetre a kérdés, majd várja a választ a tesztelőtől
     * A tesztelő válasza alapján (y/n) visszaad egy igazat vagy hamisat
     * Ha a tesztelő nem az elvárt módón válaszol akkor kiírja, hogy hiba és várja a választ
     * @param message a kérdés amit felteszünk
     * @return true vagy false a tesztelő válasza alapján
     */
    protected static boolean question(String message){
        System.out.println(Colors.GREEN + "[Question] " + Colors.RESETCOLOR + message);
        System.out.print(">");

        while(true){
            String ans = scanner.next().toLowerCase();

            if(ans.equals("y")){
                System.out.println(Colors.YELLOW + "[DECISION] "+ Colors.RESETCOLOR + message + Colors.GREEN +": yes\n" + Colors.RESETCOLOR);
                return true;
            }else if(ans.equals("n")){
                System.out.println(Colors.YELLOW + "[DECISION] " + Colors.RESETCOLOR + message + Colors.RED + ": no\n" + Colors.RESETCOLOR);
                return false;
            }else{
                System.out.println(Colors.RED + "Please answer with 'y' or 'n'" + Colors.RESETCOLOR);
                System.out.print(">");
            }
        }
    }
    /** Hibaüzenetet ír ki a standard kimenetre
     * @param message az üzenet ami kiír
     */
    protected static void error(String message){
        System.out.println(Colors.RED + "[ERROR] " + Colors.RESETCOLOR + message+"\n");
    }
    /** Kiírja az adott teszt eredményét a standard kimenetre
     * @param result az eredmény amit kiír
     */
    protected static void result(boolean result){
        System.out.println(Colors.YELLOW + "[RESULT] " + Colors.RESETCOLOR
                + (result ? Colors.GREEN + "Test successful" : Colors.RED + "Test unsuccessful")
                + "\n" + Colors.RESETCOLOR);
    }
}
