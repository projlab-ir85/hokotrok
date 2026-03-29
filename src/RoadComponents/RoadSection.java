package RoadComponents;

import Consumable.Consumable;
import Vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class RoadSection implements Updateable{
    protected int snowLevel;
    protected int iceLevel;

    protected boolean accidentHappened;
    protected int accidentTime;

    protected List<Consumable> consumables;
    protected List<Vehicle> vehicles;

    public RoadSection next;
    public RoadSection previous;
    public RoadSection left;
    public RoadSection right;

    protected Lane lane;
    protected int sublaneIndex;

    public RoadSection(Lane lane, int sublaneIndex){
        snowLevel = 0;
        iceLevel = 0;
        accidentHappened = false;
        accidentTime = 0;

        consumables = new ArrayList<>();
        vehicles = new ArrayList<>();

        next = null;
        previous = null;
        left = null;
        right = null;

        this.lane = lane;
        this.sublaneIndex = sublaneIndex;
    }

    public void snowReduce(int amount){}
    public void snowIncrease(int amount){}

    public void iceReduce(int amount){}
    public void iceIncrease(int amount){}

    public void update(){}

    //demo
    public boolean accept(Vehicle v){
        vehicles.add(v);
        return true;
    }

    public void addConsumable(Consumable c){}

    public int getSnow() {
        return snowLevel;
    }
    public int getIce() {
        return iceLevel;
    }
}
