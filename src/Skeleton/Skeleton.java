package Skeleton;

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
    /** Színek definiálása
     */
    private static final String RESETCOLOR = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String YELLOW = "\u001B[38;5;226m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[92m";
    private static final String ORANGE = "\u001B[38;5;214m";
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
                + ORANGE + caller + RESETCOLOR
                + " --> "
                + ORANGE + callee + RESETCOLOR
                + "."
                + BLUE + method + RESETCOLOR
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
                + ORANGE + callee + RESETCOLOR
                + " <-- "
                + ORANGE + caller + RESETCOLOR
                + "."
                + BLUE + method + RESETCOLOR
                + ": "
                + PURPLE + result + RESETCOLOR
        );
    }
    /** A program főmenüje
     * Felsorolja a teszteket, majd várja, hogy a tesztelő válasszon
     * A tesztelő beírja teszt számát és ekkor a program lefuttatja az adott tesztet
     * Ha a tesztelő rossz számot adott akkor jelzi a hibát
     */
    public void menu(){
        System.out.println(GREEN + "[MENU]");

        for(int i = 0; i < tests.size(); i++){
            System.out.println(BLUE + (i+1) + RESETCOLOR + " - test" + (i+1));
        }

        System.out.print(">");

        int index = scanner.nextInt();

        if(index < 1 || index > tests.size()){
            System.out.println(RED + "Invalid index." + RESETCOLOR);
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
        System.out.println(YELLOW + "[INFO] " + RESETCOLOR + message + "\n" + RESETCOLOR);
    }
    /** Kérdés függvény
     * Kiírja a szabványos kimenetre a kérdés, majd várja a választ a tesztelőtől
     * A tesztelő válasza alapján (y/n) visszaad egy igazat vagy hamisat
     * Ha a tesztelő nem az elvárt módón válaszol akkor kiírja, hogy hiba és várja a választ
     * @param message a kérdés amit felteszünk
     * @return true vagy false a tesztelő válasza alapján
     */
    protected static boolean question(String message){
        System.out.println(GREEN + "[Question] " + RESETCOLOR + message);
        System.out.print(">");

        while(true){
            String ans = scanner.next().toLowerCase();

            if(ans.equals("y")){
                System.out.println(YELLOW + "[DECISION] "+ RESETCOLOR + message + GREEN +": yes\n" + RESETCOLOR);
                return true;
            }else if(ans.equals("n")){
                System.out.println(YELLOW + "[DECISION] " + RESETCOLOR + message + RED + ": no\n" + RESETCOLOR);
                return false;
            }else{
                System.out.println(RED + "Please answer with 'y' or 'n'" + RESETCOLOR);
                System.out.print(">");
            }
        }
    }
    /** Hibaüzenetet ír ki a standard kimenetre
     * @param message az üzenet ami kiír
     */
    protected static void error(String message){
        System.out.println(RED + "[ERROR] " + RESETCOLOR + message+"\n");
    }
    /** Kiírja az adott teszt eredményét a standard kimenetre
     * @param result az eredmény amit kiír
     */
    protected static void result(boolean result){
        System.out.println(YELLOW + "[RESULT] " + RESETCOLOR
                + (result ? GREEN + "Test successful" : RED + "Test unsuccessful")
                + "\n" + RESETCOLOR);
    }
}
