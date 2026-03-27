package Main;

import Skeleton.*;

public class Main {
    public static void main(String[] args){
        Tests tests = new Tests();
        Skeleton sk = new Skeleton(tests);

        sk.menu();
    }
}
