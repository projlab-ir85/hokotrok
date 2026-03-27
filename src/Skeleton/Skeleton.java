package Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Skeleton {
    private List<Runnable> tests = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int indent = 0;

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

    private static void increaseIndent(){
        indent += 1;
    }

    private static void decreaseIndent(){
        indent -= 1;
        if(indent < 0) indent = 0;
    }

    public static void call(String caller, String callee, String method, boolean increase){
        System.out.println("\t".repeat(indent) + caller + " --> " + callee + "." + method);

        if(increase) increaseIndent();
    }

    public static void returnCall(String caller, String callee, String method, String result){
        decreaseIndent();
        System.out.println("\t".repeat(indent) + callee + " <-- " + caller + "." +method + ": " +result);
    }

    public void menu(){
        System.out.println("[MENU]");

        for(int i = 0; i < tests.size(); i++){
            System.out.println((i+1) + " - test" + (i+1));
        }

        System.out.print(">");

        int index = scanner.nextInt();

        if(index < 1 || index > tests.size()){
            System.out.println("Invalid index.");
            return;
        }

        info("Started test"+(index));
        tests.get(index-1).run();
        decreaseIndent();
    }

    private static void info(String message){
        System.out.println("[INFO] "+message);
    }

    protected static boolean question(String message){
        System.out.println("[Question] "+message);
        System.out.print(">");

        while(true){
            String ans = scanner.next().toLowerCase();

            if(ans.equals("y")){
                System.out.println("[DECISION] " + message + ": yes");
                return true;
            }else if(ans.equals("n")){
                System.out.println("[DECISION] " + message + ": no");
                return false;
            }else{
                System.out.println("Please answer with 'y' or 'n'");
            }
        }
    }

    protected static void error(String message){
        System.out.println("[ERROR] "+message);
    }

    protected static void result(boolean result){
        System.out.println("[RESULT] "+ (result ? "Test successful" : "Test unsuccessful"));
    }
}
