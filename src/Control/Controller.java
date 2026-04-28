package Control;

import java.util.Scanner;

public class Controller {
    private boolean isRunning;
    private int tickCount;
    private Scanner scanner;
    private Commands commands;

    public Controller(){
        isRunning = false;
        tickCount = 0;
        scanner = new Scanner(System.in);
        commands = new Commands(this);
    }

    public void start() throws Exception{
        isRunning = true;

        while(isRunning){
            commands.dispatch(scanner.nextLine());
        }
    }

    public void tick(){
        tickCount++;
        //lepes
    }

    public void printOutput(String message){}

    public void exit(){
        isRunning = false;
    }
}
