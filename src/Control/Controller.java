package Control;

import RoadComponents.Intersection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private boolean isRunning;
    private int tickCount;
    private Scanner scanner;
    private Commands commands;
    private List<Intersection> intersections;
    private boolean deterministic;

    public Controller(){
        isRunning = false;
        tickCount = 0;
        scanner = new Scanner(System.in);
        commands = new Commands(this);
        intersections = new ArrayList<>();
    }

    public void start() throws Exception{
        isRunning = true;

        while(isRunning){
            commands.dispatch(scanner.nextLine());
        }
    }

    public void tick(){
        tickCount++;
        for (Intersection i : intersections){
            i.tick();
        }
    }

    public void printOutput(String message){}

    public void exit(){
        isRunning = false;
    }

    public void clearGame(){
        intersections.clear();
    }

    public void setDeterministic(boolean mode){
        deterministic = mode;
    }
}
