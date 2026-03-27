package Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Skeleton {
    private List<Runnable> tests = new ArrayList<>();

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

    public void Menu(){
        System.out.println("[MENU]");

        for(int i = 0; i < tests.size(); i++){
            System.out.println((i+1) + " - test" + (i+1));
        }

        System.out.print(">");

        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();

        if(index < 1 || index > tests.size()){
            System.out.println("Invalid index.");
            return;
        }
        tests.get(index-1).run();
    }



}
