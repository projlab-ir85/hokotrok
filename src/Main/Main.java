package Main;

import Skeleton.*;

public class Main {
    /** A main függvény, itt indul a program
     * Példányosítja a teszteket és a skeletont
     * Meghívja a skeleton menu függvényét
     */
    public static void main(String[] args){
        Tests tests = new Tests();
        Skeleton sk = new Skeleton(tests);

        sk.menu();
    }
}
